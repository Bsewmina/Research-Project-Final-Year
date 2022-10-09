# %%
import pandas as pd

# %%
data = pd.read_csv('TravelTimePrediction\TrainData\TrainData.csv')

# %%


# %%


# %%


# %%
data['Dipature'].map({'Dehiwala':1,'wellawatte':2,'bambalapitiya':3,'kollupitiya':4,'kompannavidiya':5})

# %%
data['Dipature'] = data['Dipature'].map({'Dehiwala':1,'wellawatte':2,'bambalapitiya':3,'kollupitiya':4,'kompannavidiya':5})

# %%


# %%
data['Arrival'].map({'Fort ':6,'wellawatte':2,'bambalapitiya':3,'kollupitiya':4,'kompannavidiya':5})

# %%
data['Arrival'] = data['Arrival'].map({'Fort ':6,'wellawatte':2,'bambalapitiya':3,'kollupitiya':4,'kompannavidiya':5})

# %%


# %%
X = data.drop(['Time'],axis=1)

# %%
y = data['Time']

# %%
from sklearn.model_selection import train_test_split

# %%
X_train,X_test,y_train,y_test = train_test_split(X,y,test_size=0.2,random_state=42)

# %%
from sklearn.neural_network import MLPClassifier

# %%
NN = MLPClassifier()
NN.fit(X_train,y_train)

# %%
y_pred1 = NN.predict(X_test)

# %%
df1 = pd.DataFrame({'Actual':y_test, 'NN Results':y_pred1})

# %%
data = {'Dipature':1,
        'Arrival':2,
        }
df = pd.DataFrame(data,index=[0])

# %%
predit = NN.predict(df)


# %%
def GetTrainPrediction(Dipature,Arrival):
    data = {'Dipature':Dipature,
            'Arrival':Arrival,
        }
    df = pd.DataFrame(data,index=[0])
    predit = NN.predict(df)
    print('Train Travel Time Prediction :- ',*predit,' ')

# %%
def GetDipatureCode(data):
    if('Dehiwala' == data):
        return 1
    elif('wellawatte' == data):
        return 2
    elif('bambalapitiya' == data):
        return 3
    elif('kollupitiya' == data):
        return 4
    elif('kompannavidiya' == data):
        return 5

# %%
def GetArrivalCode(data):
    if('Fort' == data):
        return 6
    elif('wellawatte' == data):
        return 2
    elif('bambalapitiya' == data):
        return 3
    elif('kollupitiya' == data):
        return 4
    elif('kompannavidiya' == data):
        return 5

# %%


def GetPrediction(Dvalue,Avalue):
    Dipature = GetDipatureCode(Dvalue)
    Arrival = GetArrivalCode(Avalue)

    GetTrainPrediction(Dipature,Arrival)


GetPrediction('Dehiwala','Fort')