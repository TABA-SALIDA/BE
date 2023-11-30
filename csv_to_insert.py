import pandas as pd
import pyodbc
import subprocess
import os

# Directory to watch
directory_to_watch = "/home/ec2-user/data"

# Database connection details
DSN = 'tibero6'
username = 'sys'
password = 'tibero'


conn = pyodbc.connect(f'DSN={DSN};UID={username};PWD={password}')
conn.setdecoding(pyodbc.SQL_CHAR, encoding='utf-16')
conn.setdecoding(pyodbc.SQL_WCHAR, encoding='utf-16')
conn.setencoding(encoding='utf-8')

cursor = conn.cursor()


# Function to insert data from CSV to database
def insert_data_from_csv(file_path):
    # Read CSV file
    df = pd.read_csv(file_path)
    # Assuming table and columns already exist and match CSV format
    # Replace 'YourTableName' with your actual table name


    for index, row in df.iterrows():
        insert_query = f'''
    INSERT INTO emergency_news
    VALUES (s_news_id.NEXTVAL, 
            ?, 
            ?,
            ?, 
            ?, 
            'earthquack')
    '''
        values = [row['title'], row['content'], row['summary'], row['link']]
        cursor.execute(insert_query, values)

    conn.commit()

# Monitoring the directory
process = subprocess.Popen(['inotifywait', '-m', '-e', 'create', '-e', 'modify','-e', 'close_write', '--format', '%w%f', directory_to_watch], stdout=subprocess.PIPE)

while True:
    line = process.stdout.readline()
    if not line:
        break
    filename = line.strip().decode()

    # Check if the file is a CSV
    if filename.endswith('.csv'):
        print(filename)
        insert_data_from_csv(filename)

