from flask import Flask, render_template, jsonify, request
import requests
import sys, os
from bs4 import BeautifulSoup
import json
import time
from module.nikeDraw import showNikeDraw

app = Flask(__name__)


@app.route('/nikeDraw', methods=['GET'])
def nikeDraw():
    return render_template('nikeDraw.html')


@app.route('/nikeDrawData', methods=['GET'])
def nikeDrawData():
    page = int(request.args.get('page'))
    Result = showNikeDraw(page)

    return Result


@app.route('/casinaDraw')
def casinaDraw():
    return 'This is Home!'


@app.route('/')
def home():
    return render_template('index.html')


if __name__ == '__main__':
    app.run('0.0.0.0', port=4000, debug=True)
