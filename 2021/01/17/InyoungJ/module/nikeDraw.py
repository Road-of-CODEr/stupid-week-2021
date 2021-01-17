# -*- coding: utf-8 -*-
from flask import Flask, render_template, jsonify
import requests
from bs4 import BeautifulSoup
import json
import math


def showNikeDraw(page):
    headers = {
        'User-Agent': 'Mozilla/5.0 (Windows NT 10.0; Win64; x64)AppleWebKit/537.36 (KHTML, like Gecko) Chrome/73.0.3683.86 Safari/537.36'}
    data = requests.get('https://www.nike.com/kr/launch/?type=upcoming&activeDate=date-filter:AFTER', headers=headers)
    soup = BeautifulSoup(data.text, 'html.parser')
    events = soup.select('body > section > section > section > div.category-max-width > div > ul > li')

    result = []
    for event in events:
        launchingTime = event['data-active-date']
        itemImage = event.select_one('img')['data-src']
        itemName = event.select_one('a')['title']
        itemUrl = "https://www.nike.com"+event.select_one('a')['href']

        print(launchingTime, itemName, itemImage, itemUrl)
        stringResult = {
            "name": itemName,
            "date": launchingTime,
            "image": itemImage,
            "url": itemUrl
        }

        result.append(stringResult)
    pageSize = math.ceil((len(events) / 3))
    return jsonify({'DrawResult': result[(page - 1) * 3:page * 3], 'pageSize': pageSize})
# 03, 36, 69
