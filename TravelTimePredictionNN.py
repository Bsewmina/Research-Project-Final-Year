# %%
import pandas as pd

# %%
data = pd.read_csv('BusTravelData.csv')


# %%
data['Day'].unique()

# %%
data['Day'].map({'Sunday':0,'Monday':1,'Tuesday':2})

# %%
data['Day'] = data['Day'].map({'Sunday':0,'Monday':1,'Tuesday':2})

# %%
data.head()

# %%
data['Special'] = data['Special'].map({'No':0,'Yes':1})

# %%
data.tail()

# %%
data['Weather'].unique()

# %%
data.columns

# %%
X = data.drop(['Date','Travel Time'],axis=1)


# %%
y = data['Travel Time']


# %% [markdown]
# Train/test split
# 
# 1. split data into two parts 
# 
#     training data set
#     testing data set
# 
# 2. Train the models on training set
# 
# 3. Test the models on testing data set

# %%
from sklearn.model_selection import train_test_split

# %%
X_train,X_test,y_train,y_test = train_test_split(X,y,test_size=0.2,random_state=42)

# %%
y_train

# %%
from sklearn.linear_model import LinearRegression
from sklearn.svm import SVR
from sklearn.ensemble import RandomForestRegressor
from sklearn.ensemble import GradientBoostingClassifier
from sklearn.neural_network import MLPClassifier


# %%
lr = LinearRegression()
NN = MLPClassifier()
lr.fit(X_train,y_train)
NN.fit(X_train,y_train)

# %%
y_pred1 = lr.predict(X_test)

# %%
df1 = pd.DataFrame({'Actual':y_test, 'Lr Results':y_pred1})

# %%
df1

# %%
y_pred2 = NN.predict(X_test)
df2 = pd.DataFrame({'Actual':y_test, 'NN Results':y_pred2, 'LR Results':y_pred1})
df2

# %%
import matplotlib.pyplot as plt

# %%
plt.subplot(221)
plt.plot(df1['Actual'].iloc[0:11],label='Actual')
plt.plot(df1['Lr Results'].iloc[0:11],label='Lr Results')
plt.legend()

# %% [markdown]
# 	Time	Day	Rush	Special	Congestion	Drving Speed	Stops	Weather	Distance	Travel Time

# %%
plt.subplot(221)
plt.plot(df1['Actual'].iloc[0:11],label='Actual')
plt.plot(df2['NN Results'].iloc[0:11],label='NN Results')
plt.legend()

# %% [markdown]
# Prediction with Current parameters (Predict Time with trained Model)

# %%
data = {'Time':6,
        'Day':1,
        'Special':0,
        'Congestion':8,
        'Drving Speed':40,
        'Stops':14,
        'Weather':23,
        'Distance':14.7
        }
df = pd.DataFrame(data,index=[0])
df


# %%
new_pred = lr.predict(df)
NN_pred = NN.predict(df)


# %%
data = {'Time':17,
        'Day':1,
        'Special':0,
        'Congestion':5,
        'Drving Speed':40,
        'Stops':20,
        'Weather':24,
        'Distance':14.7
        }
ddf = pd.DataFrame(data,index=[0])
ddf

# %%
resultsNN = NN.predict(ddf)
resultsNN

# %%
def GetPrediction(time,date,special,congestion,dspeed,stops,weather,distance):
    data = {'Time':time,
        'Day':date,
        'Special':special,
        'Congestion':congestion,
        'Drving Speed':dspeed,
        'Stops':stops,
        'Weather':weather,
        'Distance':distance
        }
    ddf = pd.DataFrame(data,index=[0])
    resultsNN = NN.predict(ddf)
    resultsNN
    print('Travel Time Prediction :-',*resultsNN,'Min')

# %%
def GetDateCode(date):
    if('Sunday' == date ):
        return 0
    elif('Monday' == date):
        return 1
    elif('Tuesday' == date):
        return 2
    elif('Wednesday' == date):
        return 3
    elif('Thursday' == date):
        return 4
    elif('Friday' == date):
        return 5
    elif('Saturday' == date):
        return 6
    else:
        return 9
    
import testingWeather

city = "malabe"
city = city+" weather"
 
time= 6  #this time come from semins data/member of route planning
day ='Friday'  #date come from UI 
date = GetDateCode(day)
special = 0 # Special Day or Not 1/0
Congestion= 8 # This is depend on the depature time---- heavy traffic =9 / No Traffic = 0 
drivingspeedAVG = 40 #this is come from GPS data
stops =14 #total Bus stops/holts between the travel --- data come from the Seminas data/member of route planning 
weather =testingWeather.weather(city)  #Get weather in travel area
Distance =14.7 #Distance data come from seminas ----- data come from the Seminas data/member of route planning


GetPrediction(time,date,special,Congestion,drivingspeedAVG,stops,weather,Distance)





