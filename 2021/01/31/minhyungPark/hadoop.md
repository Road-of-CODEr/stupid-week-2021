# Hadoop

- Hadoop
- HDFS
- Map-Reduce
- Hbase
- YARN
- Hive
- Impala
- Spark

## Hadoop

- https://hadoop.apache.org/

대용량 데이터를 분산 처리할 수 있는 오픈소스 프레임워크이다. 구글이 발표한 논문들을 기반으로 한 구현체이다. HDFS에 데이터를 저장하고, MR(Map Reduce)를 이용하여 데이터를 처리한다.


![ecosystem](https://user-images.githubusercontent.com/46305139/106387599-cf51b600-641d-11eb-815e-b5f729d4a5a2.png)


## HDFS

- https://hadoop.apache.org/docs/current/hadoop-project-dist/hadoop-hdfs/HdfsDesign.html

대용량의 파일을 특정크기(2.0에서는 128MB)의 블록단위로 나누어 분산된 서버에 저장한다.

![hdfsarchitecture](https://user-images.githubusercontent.com/46305139/106386976-f064d780-641a-11eb-9ee0-244caa65e627.png)



## Map Reduce

- https://hadoop.apache.org/docs/current/hadoop-mapreduce-client/hadoop-mapreduce-client-core/MapReduceTutorial.html
- https://www.todaysoftmag.com/article/1358/hadoop-mapreduce-deep-diving-and-tuning


이름에서 유추할수 있다시피 데이터(HDFS에 저장되어 있는)를 매핑 & 리듀스 작업을 하는 프레임워크이다. 기본적으로 자바 어플리케이션으로 이루어져 있다.

![mr](https://user-images.githubusercontent.com/46305139/106388357-76841c80-6421-11eb-9c85-0c7f7ce0153c.png)


