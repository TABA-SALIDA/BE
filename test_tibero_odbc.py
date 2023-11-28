import pyodbc


sql = 'SELECT * FROM emergency_news;'

print(1)
#conn = pyodbc.connect('DSN=MYCODE; User=sys; Password=tibero')

db = pyodbc.connect('DSN=tibero6;UID=sys;PWD=tibero')
print(2)
#conn.setdecoding(pyodbc.SQL_CHAR, encoding='utf-8')
print(3)
#conn.setdecoding(pyodbc.SQL_WCHAR, encoding='utf-8')
print(4)
#conn.setdecoding(pyodbc.SQL_WMETADATA, encoding='utf-32le')
print(5)
#conn.setencoding(encoding='utf-8')

print(6)
cursor = db.cursor()
row = cursor.execute(sql)
    


while True:
    print('in True')
    row = cursor.fetchone()
    if not row:
        break
    print(row)

conn.close()

