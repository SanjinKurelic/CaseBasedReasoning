import pandas
import numpy
import math


class ScriptSetting:
    csv_file_name = 'telecom.csv'
    csv_separator = ','
    csv_null_values = 'null'
    csv_true_values = 'true'
    csv_false_values = 'false'
    column_identifier = 'customerId'
    columns_out_of_prediction = ['customerId']


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


# Return only columns in defining criteria
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
def get_predict_columns(data_frame, missing_column):
    return subset_columns(data_frame, [missing_column] + ScriptSetting.columns_out_of_prediction)


# Calculate Euclid distance
def euclid_distance(data_frame, missing_column):
    # All rows except missing ones for missing_column
    df_removed_missing_rows = data_frame.dropna(subset=[missing_column]).copy()
    # All rows that contain missing values for missing_column
    df_missing_rows = data_frame[data_frame.isnull().unstack()[missing_column]].copy()
    # Columns that we will use for calculating euclid distance
    columns = get_predict_columns(data_frame, missing_column)
    # Found values that we return
    found_values = {}

    # For every row that is not missing value
    for index_missing, df_missing_row in df_missing_rows.iterrows():
        value = ''
        min_distance = None

        # For every row that do not contain missing values
        for index_removed, df_removed_row in df_removed_missing_rows.iterrows():
            current_distance = 0

            # (a1 - b1)^2 + (a2 - b2)^2 + ... + (an - bn)^2
            for column in columns:
                current_distance += math.pow(df_missing_row[column] - df_removed_row[column], 2)
            # sqrt from up expression
            current_distance = math.sqrt(current_distance)

            # If that is min distance save it
            if (min_distance is None) or (min_distance > current_distance):
                min_distance = current_distance
                value = df_removed_row[missing_column]

        # Save founded values
        found_values[str(data_frame.loc[index_missing][ScriptSetting.column_identifier])] = str(value)

    return found_values


def start():
    pandas.set_option('display.max_columns', None)
    df = load_data_frame_from_csv(ScriptSetting.csv_file_name)

    # Normalize (z-scale) all numerical columns except columns containing missing values
    normalize_data(df)

    # Transform all alpha columns to numbers
    columns_to_number(df)

    # Find missing values
    found_values = {}
    for missing_column in get_missing_columns(df):
        found_values.update(euclid_distance(df, missing_column))

    for key, val in found_values.items():
        print(str(key) + ' => ' + str(val))


if __name__ == "__main__":
    start()
