# Missing Values

Case-based reasoning (CBR) is the process of solving new problems based on the solutions of similar past problems <sup>[1]</sup>. In data science CBR is a technique which allows us to find missing values from a given set of data, and each variable has its own set of characteristics. If we have a variable that does not contain one or more characteristic, we can find a similar variable using Euclidean distance and predict the missing characteristics.

In this repository there are 2 Python scripts, one for finding missing values and the other for calculating which characteristics (columns) influence/define the missing value the most. There is also data generator written in Java in a folder named *CaseBasedReasoning Generator*. Besides the aforementioned scripts and generator there is already a generated file containing data (including missing values) and statistics collected from users of a telecommunication company. Using that data we can train a model and check the correctness of the results.

## Getting started

### Requirements

To run both scripts you should have **Python version 3.x** and the following modules installed:

- pandas
- numpy

All modules are available trough PIP:

```bash
pip install pandas
```

This command also installs numpy module.

To run the Java generator you should have **Java version 8** or newer installed on your machine. Java generator **is not required** for demonstrating this algorithm.

### Running

This git repository is consisted of the following files:

- *FindMissingValues.py*
- *CalculateIV.py*
- *telecom.csv*
- *telecomStats.txt*
- *CaseBasedReasoning Generator* project

File *telecom.csv* contains all data from the telecommunication company in *csv* format with columns:

- **customerId** : int
- **customerAge** : int
- **customerPlansChanged** : int
- **smsCountPerMonth** : int
- **callMinutePerMonth** : int
- **dataMBPerMonth** : int
- **netflixStream** : boolean
- **pickboxStream** : boolean
- **youtubeStream** : boolean
- **hboGoStream** : boolean
- **viberFree** : boolean
- **whatsappFree** : boolean

There is a total of 4 000 rows and 12 columns. Out of the 4 000 rows, 6 of them have missing values: 3 rows with **customerAge** missing and 3 rows with **customerPlansChanged** missing.

If we run the script *FindMissingValues.py* with the following command:

```bash
python3 FindMissingValues.py
```

we get following results:

customerId | predictedValue | realValue
:---: | :---: | :---:
3998 | 30 | 33
3999 | <span style="color:red">**70**</span> | 48
4000 | 25 | 28
3995 | 0 | 0
3996 | 3 | 3
3997 | 4 | 5

As we can see our predicting model is giving pretty good values, except for people older than 40. Those people usually do not use any kind of data/sms/call plans, and for that reason they are not our target population for presenting new tariffs. It should also be mentioned that data used in prediction is quite random so edge cases are not deeply covered (ex: people older than 40 years old).

If we run the generator, its output will be *telcom.csv* and *telcomStats.txt* files. The file *telcomStats.txt* gives us information about the distribution of the generated data.

If we run *CalculateIV.py* we will get information about which columns influence missing value (in this case customer age is the column that contains missing values):

```bash
(14, 18):
#Positive influence:
customerPlansChanged = (2.0, 3.0)
#Opposite influence:
customerPlansChanged = (3.0, 4.0)
netflixStream = True
pickboxStream = True
youtubeStream = True
hboGoStream = True

(18, 28):
#Positive influence:
youtubeStream = True
#There is no opposite influence
...
```

From this partial output you can see which variables influence the missing value, so if a user changes his/her tariff 2 or 3 times (**positive influence**), and does not have Netflix, Pickbox, Youtube or HBO GO stream (**negative influence**), than he/she is probably between 14 and 18 years old.

## Notice

This is a simple algorithm for finding missing values and it is not tested on real world data/applications. Do not use it in production before you double check if everything is working as assumed.

## License

This project is licensed under the MIT License - see the *LICENSE* file for details.

## References

<sup>[1]</sup> https://en.wikipedia.org/wiki/Case-based_reasoning