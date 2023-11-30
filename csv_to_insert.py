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

try:
    conn = pyodbc.connect(f'DSN={DSN};UID={username};PWD={password}')
    conn.setdecoding(pyodbc.SQL_CHAR, encoding='utf-16')
    conn.setdecoding(pyodbc.SQL_WCHAR, encoding='utf-16')
    conn.setencoding(encoding='utf-8')

    cursor = conn.cursor()
except Exception as e:
    print(f"Database connection failed: {e}")
    exit(1)

# Function to insert data from CSV to database
def insert_data_from_csv(file_path):
    try:
        df = pd.read_csv(file_path)
    except Exception as e:
        print(f"Error reading file {file_path}: {e}")
        return

    for index, row in df.iterrows():
        try:
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
        except Exception as e:
            print(f"Failed to insert data: {e}")

    try:
        conn.commit()
    except Exception as e:
        print(f"Failed to commit transaction: {e}")

# Monitoring the directory
try:
    process = subprocess.Popen(['inotifywait', '-m', '-e', 'create', '-e', 'modify','-e', 'close_write', '--format', '%w%f', directory_to_watch], stdout=subprocess.PIPE)
except Exception as e:
    print(f"Failed to start directory monitoring: {e}")
    exit(1)

while True:
    try:
        line = process.stdout.readline()
        if not line:
            break
        filename = line.strip().decode()

    # Check if the file is a CSV
        if filename.endswith('.csv'):
            insert_data_from_csv(filename)
    except Exception as e:
        print(f"Error during file processing: {e}")

