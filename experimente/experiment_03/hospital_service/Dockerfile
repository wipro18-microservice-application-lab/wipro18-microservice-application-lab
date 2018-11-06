FROM python:3.6.1-alpine


RUN mkdir /usr/src/app
WORKDIR /usr/src/app

COPY . .
RUN pip install -r requirements.txt

EXPOSE 5001
CMD python ./app.py