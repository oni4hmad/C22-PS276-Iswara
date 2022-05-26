# import library
import json
from string import punctuation
import random
import pickle
from tensorflow.keras.models import load_model

with open("src/humanText.json") as data_file:
    data = json.load(data_file)
model = load_model('modelLSTM.tf')
le_filename = open("label_encoder.pickle", "rb")
le = pickle.load(le_filename)
le_filename.close()

def preprocess_string(string):
    string = string.lower()
    exclude = set(punctuation)
    string = ''.join(ch for ch in string if ch not in exclude)
    return string

def chat(model):
    print("Halo disini Ara, apakah kamu akan melaporkan kasus yang orang lain alami sebagai korban?")
    exit = False
    exitList = ['exit', 'terimakasih', 'bye', 'bye', 'terima kasih']
    while not exit:
        inp = input("Badarawuhi: ")
        inp = preprocess_string(inp)
        prob = model.predict([inp])
        results = le.classes_[prob.argmax()]
        if prob.max() < 0.2:
            print("Ara: Mohon maaf kak, Ara belum mengerti maksud kakak")
            print("Ara: Kakak dapat memilih opsi pelaporan, untuk mempermudahkan Ara memahami")
        elif inp in exitList:
            print("Ara: Chat another time")
            break
        else:
            for tg in data['intents']:
                if tg['tag'] == results:
                    responses = tg['responses']
            if results == 'bye':
                exit = True
                print("END CHAT")
            print(f"Ara: {random.choice(responses)}")

if __name__ == "__main__":
    chat(model)