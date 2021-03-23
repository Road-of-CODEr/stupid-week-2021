using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class EnemyManager : MonoBehaviour
{
    public static EnemyManager Instance = null;

    float currentTime;
    public float createTime = 1;
    public GameObject enemyFactory;
    float minTime = 0.5f;
    float maxTime = 1.5f;

    //메모리풀
    public int poolSize = 10;
    
    //Spawn point
    public Transform[] spawnPoints;

    private int a = 0;
    private List<GameObject> enemyObjectPool;

    void Awake()
    {
        if (Instance == null)
        {
            Instance = this;
        }
    }

    // Start is called before the first frame update
    void Start()
    {
        createTime = UnityEngine.Random.Range(minTime, maxTime);
        enemyObjectPool = new List<GameObject>();
        print(poolSize);
        for(int i=0;i< poolSize; i++)
        {
            GameObject enemy = Instantiate(enemyFactory);
            enemyObjectPool.Add(enemy);
            enemy.SetActive(false);
        }
    }

    // Update is called once per frame
    void Update()
    {
        currentTime += Time.deltaTime;
        if (currentTime > createTime)
        {
            if (enemyObjectPool.Count > 0)
            {
                GameObject enemy = enemyObjectPool[0];
                enemyObjectPool.Remove(enemy);
                enemy.SetActive(true);
                int index = UnityEngine.Random.Range(0, spawnPoints.Length);
                enemy.transform.position = spawnPoints[index].position;
            }
            createTime = UnityEngine.Random.Range(minTime, maxTime);
            currentTime = 0;
        }
    }

    public void setEnemyObject(GameObject eb)
    {
        enemyObjectPool.Add(eb);
    }
}
