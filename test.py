import pyodbc
#conn_str = 'DRIVER={Tibero 6 ODBC Driver};SERVER=localhost;PORT=8629;DATABASE=tibero;UID=sys;PWD=tibero;'

conn_str = f"DSN=tibero6; UID=sys; PWD=tibero"
db = pyodbc.connect(conn_str)

cursor = db.cursor()



cursor.execute('select * from all_users;')

data = cursor.fetchall()



for x in data:

    print (x[0]) 

cursor.close()

db.close()
