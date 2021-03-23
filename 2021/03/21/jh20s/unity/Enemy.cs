using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Enemy : MonoBehaviour
{
    // Start is called before the first frame update
    public float speed = 5;
    Vector3 dir;
    
    public GameObject explosionFactory;

    void OnEnable()
    {
        int randValue = UnityEngine.Random.Range(0, 10);

        if (randValue < 3)
        {
            GameObject target = GameObject.Find("Player");
            dir = target.transform.position - transform.position;
            dir.Normalize();
        }
        else
        {
            dir = Vector3.down;
        }
    }

    void Start()
    {
        
        int randValue = UnityEngine.Random.Range(1, 10);

        if (randValue < 3)
        {
            GameObject target = GameObject.Find("Player");
            dir = target.transform.position - transform.position;
            dir.Normalize();
        }
        else
        {
            dir = Vector3.down;
        }
    }

    // Update is called once per frame
    void Update()
    {
        transform.position += dir * speed * Time.deltaTime;
    }
    private void OnCollisionEnter(Collision other)
    {
        //적파괴시 점수 계산
        ScoreManager.Instance.Score++;
        //폭발효과
        GameObject explosion = Instantiate(explosionFactory);
        explosion.transform.position = transform.position;

        //부딪친 객체 삭제
        if (other.gameObject.name.Contains("Bullet"))
        {
            other.gameObject.SetActive(false);

            PlayerFire player = GameObject.Find("Player").GetComponent<PlayerFire>();
            player.bulletObjectPool.Add(other.gameObject);
        }
        else
        {
            Destroy(other.gameObject);
        }
        //enemy. 자기자신 삭제
        gameObject.SetActive(false);

        //enemy장전
        EnemyManager.Instance.setEnemyObject(other.gameObject);
    }

}
