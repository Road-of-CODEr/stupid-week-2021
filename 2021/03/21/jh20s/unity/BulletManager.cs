using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class BulletManager : MonoBehaviour
{
    /*
     
       API
       1. createBullet
          초기 시작할때 싱글톤패턴으로 Instance를 만들어 제공한다.

       2. Shooting
          PlayerFire는 총알이 있는지, 어떤총알인지 알 필요가 없다. 총알을 쏜다.
          BulletManager내에서 총알의 개수, 총알의 타입을 확인하여 촌다.

        3. ChnageItem
           Player가 아이템을 먹으면 총알의 상태가 변한다.


       총알 자체는 Strategy패턴으로 관리??
       총알마다 cs파일을 만든다? 일단 1개를 만들어보자 
       총알에는 총알개수, 총알 속도, 총알Prefabs등이 정해지게 된다.

      

     */
    public static BulletManager Instance = null;

    //public BulletDefault bulletDeufalt;
    void Awake()
    {
        if (Instance == null)
        {
            Instance = this;
        }
    }
    void Start()
    {
        
    }

    void Update()
    {
        
    }
}
