import pyodbc


sql = 'SELECT * FROM emergency_news;'

# 데이터베이스 연결
conn = pyodbc.connect('DSN=tibero6;UID=sys;PWD=tibero;CHARSET=UTF8')

# 인코딩 설정
conn.setdecoding(pyodbc.SQL_CHAR, encoding='utf-16')
conn.setdecoding(pyodbc.SQL_WCHAR, encoding='utf-16')
conn.setencoding(encoding='utf-8')


cursor = conn.cursor()
cursor.execute(sql)
    


while True:
    row = cursor.fetchone()
    if not row:
        break
    fixed_row = [col.decode('utf-16le') if isinstance(col, bytes) else col for col in row]
    print(fixed_row)

conn.close()

