using System;
using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class DestroyZone : MonoBehaviour
{
    
    // Start is called before the first frame update
    void Start()
    {
        
    }

    // Update is called once per frame
    void Update()
    {
        
    }

    private void OnTriggerEnter(Collider other)
    {
        ScoreManager.Instance.Score++;
        if (other.gameObject.name.Contains("Bullet")||
            other.gameObject.name.Contains("Enemy"))
        {
            other.gameObject.SetActive(false);
            if (other.gameObject.name.Contains("Bullet"))
            {

                PlayerFire player = GameObject.Find("Player").GetComponent<PlayerFire>();
                player.bulletObjectPool.Add(other.gameObject);
            }
            else if (other.gameObject.name.Contains("enemy"))
            {
                EnemyManager.Instance.setEnemyObject(other.gameObject);
                //GameObject emObject = GameObject.Find("EnemyManager");
                //EnemyManager manager = emObject.GetComponent<EnemyManager>();
                //manager.enemyObjectPool.Add(other.gameObject);
            }
        }
    }

}
