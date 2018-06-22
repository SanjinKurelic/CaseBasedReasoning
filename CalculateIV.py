import pandas
import numpy


class ScriptSetting:
    csv_file_name = 'telecom.csv'
    csv_separator = ','
    csv_null_values = 'null'
    csv_true_values = 'true'
    csv_false_values = 'false'
    columns_out_of_prediction = ['customerId']
    missing_column = 'customerAge'
    missing_column_range = [14, 18, 28, 35, 40, 60, 150]
    negative_influence = 'negative'
    positive_influence = 'positive'


def load_data_frame_from_csv(file_name):
    data_frame = pandas.read_csv(file_name,
                                 sep=ScriptSetting.csv_separator,
                                 na_values=ScriptSetting.csv_null_values,
                                 true_values=ScriptSetting.csv_true_values,
                                 false_values=ScriptSetting.csv_false_values
                                 )

    print("Number of rows: " + str(len(data_frame.index)))
    print("Number of columns: " + str(len(data_frame.columns)))
    return data_frame


# Return list of columns that contain missing values
def get_missing_columns(data_frame):
    return data_frame.columns[data_frame.isna().any()].tolist()


def subset_columns(data_frame, columns):
    return [column for column in data_frame.columns if column not in columns]


# Convert all string columns to numerical columns by adding numerical categories
def columns_to_number(data_frame):
    for column in data_frame.select_dtypes(exclude=[numpy.number, numpy.bool_]).columns:
        data_frame[column] = pandas.Series(data_frame.loc[:, column].astype('category').cat.codes,
                                           index=data_frame.index)
    return data_frame


# Normalize all numeric columns that do not contain null values (Z-scaling)
def normalize_data(data_frame):
    # Non-null columns
    columns = subset_columns(data_frame, get_missing_columns(data_frame) + ScriptSetting.columns_out_of_prediction)
    # Numerical columns
    for column in data_frame[columns].select_dtypes(include=numpy.number).columns:
        data_frame[column] = (data_frame[column] - data_frame[column].mean()) / data_frame[column].std(ddof=0)
    return data_frame


# List of columns for which we will count Euclid distance
def get_predict_columns(data_frame):
    return subset_columns(data_frame, [ScriptSetting.missing_column] + ScriptSetting.columns_out_of_prediction)


# Calculating Information Value (IV) and Weight of Evidence (WoE) for every column
def count_iv_woe(data_frame, age_range):
    column_influence = {ScriptSetting.positive_influence: {}, ScriptSetting.negative_influence: {}}

    # Make copy of data frame and add target variable
    df_iv_woe = data_frame.copy()
    age_range_name = str(age_range).replace(']', '').replace(', ', '-').replace('(', '')
    age_range_name = 'range_' + age_range_name
    df_iv_woe[age_range_name] = 'F'
    # Set target variable to current age range
    df_iv_woe.loc[df_iv_woe[ScriptSetting.missing_column] == age_range, age_range_name] = 'T'

    # For each column calculate IV and Woe
    for column in get_predict_columns(data_frame):
        i_v = pandas.crosstab(df_iv_woe[column], df_iv_woe[age_range_name]).apply(lambda c: c / c.sum(), axis=0)
        i_v = i_v.replace(0.0, 0.00001)

        i_v['WoE'] = numpy.log(i_v['T'] / i_v['F'])
        i_v['IV'] = (i_v['T'] - i_v['F']) * numpy.log(i_v['T'] / i_v['F'])

        # If we have variables with IV > 0.5, add them to influence columns
        if len(i_v[i_v['IV'] > 0.5].index) > 0:
            for IV_index, IV_row in i_v[i_v['IV'] > 0.5].iterrows():
                influence_type = ScriptSetting.positive_influence
                # If WoE is negative, this variable has the opposite result than target variable
                if IV_row['WoE'] < 0:
                    influence_type = ScriptSetting.negative_influence

                column_influence[influence_type][column] = IV_index

    return column_influence


# Group data in ranges
def group_data(data_frame):
    columns_group = get_predict_columns(data_frame)
    df_ranged = data_frame.copy()
    # For every numeric column, we create 5 (or less) ranges
    for column in df_ranged[columns_group].select_dtypes(include=numpy.number).columns:
        column_bin = pandas.qcut(df_ranged[column], 5, duplicates='drop')
        df_ranged[column] = column_bin

    # We have grouped missing column in special defined ranges
    age_bin = pandas.cut(df_ranged[ScriptSetting.missing_column], ScriptSetting.missing_column_range)
    df_ranged[ScriptSetting.missing_column] = age_bin

    column_influence = {}
    # For every age range calculates influence of other columns
    for age_id, age_range in age_bin.drop_duplicates().iteritems():
        if pandas.isnull(age_range):
            continue

        column_influence[age_range] = count_iv_woe(df_ranged, age_range)

    return column_influence


def start():
    pandas.set_option('display.max_columns', None)
    df = load_data_frame_from_csv(ScriptSetting.csv_file_name)

    # Normalize (z-scale) all numeric columns except columns containing missing values
    normalize_data(df)

    # Transform all alpha columns to number columns
    columns_to_number(df)

    # Remove all missing rows except the one in the defining missing column
    columns = get_missing_columns(df)
    columns.remove(ScriptSetting.missing_column)
    df = df.dropna(subset=columns).copy()

    # Get columns that influences missing column
    column_influences = group_data(df)
    # Output dictionary in user readable form
    for a_range, influences in column_influences.items():
        print('\n' + str(a_range) + ':')
        if len(influences[ScriptSetting.positive_influence]) > 0:
            print('Positive influence:')
            for variable, value in influences[ScriptSetting.positive_influence].items():
                print(str(variable) + ' = ' + str(value))
        else:
            print('There is no positive influence')

        if len(influences[ScriptSetting.negative_influence]) > 0:
            print('Opposite influence:')
            for variable, value in influences[ScriptSetting.negative_influence].items():
                print(str(variable) + ' = ' + str(value))
        else:
            print('There is no opposite influence')


if __name__ == "__main__":
    start()
