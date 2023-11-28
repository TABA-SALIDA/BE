from flask import Flask
import pyodbc

connection_string = 'DSN=MYCODE;UID=sys;PWD=tibero'
connection = pyodbc.connect(connection_string)
cursor = connection.cursor()

app = Flask(__name__)

select_query = '''
select * from emergency_news;
'''

cursor.execute(select_query)

@app.route('/')
def hello_world():

    while True:
        row = cursor.fetchone()
        if not row:
            break
        print(row)

    connection.close()

    return 'Hello World!'


# Press the green button in the gutter to run the script.
if __name__ == '__main__':
    app.run()

