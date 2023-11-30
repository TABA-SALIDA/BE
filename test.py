import pyodbc


DSN = 'tibero6'
username = 'sys'
password = 'tibero'

conn = pyodbc.connect(f'DSN={DSN};UID={username};PWD={password}')

conn.setdecoding(pyodbc.SQL_CHAR, encoding='utf-16')
conn.setdecoding(pyodbc.SQL_WCHAR, encoding='utf-16')
conn.setencoding(encoding='utf-8')

cursor = conn.cursor()

select_query = "select * from emergency_news"

insert_query = '''
INSERT INTO emergency_news
VALUES(s_news_id.NEXTVAL, '기사 제목', '기사 내용', '기사 요약', '기사 링크', 'earthquack');
'''
cursor.execute(insert_query)
conn.commit()

cursor.execute(select_query)
data = cursor.fetchall()
for x in data:

    print (x[0]) 

cursor.close()

conn.close()
