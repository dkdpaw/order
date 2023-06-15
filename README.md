
# Table of contents

상품주문 시나리오

# 서비스 시나리오

요구사항
1. 고객이 주문을 한다.
2. 재고를 체크 하고 재고가 있으면 주문이 진행된다.
3. 고객이 결제를 한다.
4. 결제가 완료되면 배달을 시작한다.
5. 배송이 시작되면 재고가 차감된다.
6. 고객이 주문을 취소하면 배송이 취소된다.
7. 배송이 취소되면 재고가 다시 증가한다.

# 체크포인트

- 분석 설계
  - 이벤트스토밍: 
    - 스티커 색상별 객체의 의미를 제대로 이해하여 헥사고날 아키텍처와의 연계 설계에 적절히 반영하고 있는가?
    - 각 도메인 이벤트가 의미있는 수준으로 정의되었는가?
    - 어그리게잇: Command와 Event 들을 ACID 트랜잭션 단위의 Aggregate 로 제대로 묶었는가?
    - 기능적 요구사항과 비기능적 요구사항을 누락 없이 반영하였는가?    

  - 서브 도메인, 바운디드 컨텍스트 분리
    - 팀별 KPI 와 관심사, 상이한 배포주기 등에 따른  Sub-domain 이나 Bounded Context 를 적절히 분리하였고 그 분리 기준의 합리성이 충분히 설명되는가?
      - 적어도 3개 이상 서비스 분리
    - 폴리글랏 설계: 각 마이크로 서비스들의 구현 목표와 기능 특성에 따른 각자의 기술 Stack 과 저장소 구조를 다양하게 채택하여 설계하였는가?
    - 서비스 시나리오 중 ACID 트랜잭션이 크리티컬한 Use 케이스에 대하여 무리하게 서비스가 과다하게 조밀히 분리되지 않았는가?
  - 컨텍스트 매핑 / 이벤트 드리븐 아키텍처 
    - 업무 중요성과  도메인간 서열을 구분할 수 있는가? (Core, Supporting, General Domain)
    - Request-Response 방식과 이벤트 드리븐 방식을 구분하여 설계할 수 있는가?
    - 장애격리: 서포팅 서비스를 제거 하여도 기존 서비스에 영향이 없도록 설계하였는가?
    - 신규 서비스를 추가 하였을때 기존 서비스의 데이터베이스에 영향이 없도록 설계(열려있는 아키택처)할 수 있는가?
    - 이벤트와 폴리시를 연결하기 위한 Correlation-key 연결을 제대로 설계하였는가?

  - 헥사고날 아키텍처
    - 설계 결과에 따른 헥사고날 아키텍처 다이어그램을 제대로 그렸는가?
    
- 구현
  - [DDD] 분석단계에서의 스티커별 색상과 헥사고날 아키텍처에 따라 구현체가 매핑되게 개발되었는가?
    - Entity Pattern 과 Repository Pattern 을 적용하여 JPA 를 통하여 데이터 접근 어댑터를 개발하였는가
    - [헥사고날 아키텍처] REST Inbound adaptor 이외에 gRPC 등의 Inbound Adaptor 를 추가함에 있어서 도메인 모델의 손상을 주지 않고 새로운 프로토콜에 기존 구현체를 적응시킬 수 있는가?
    - 분석단계에서의 유비쿼터스 랭귀지 (업무현장에서 쓰는 용어) 를 사용하여 소스코드가 서술되었는가?
  - Request-Response 방식의 서비스 중심 아키텍처 구현
    - 마이크로 서비스간 Request-Response 호출에 있어 대상 서비스를 어떠한 방식으로 찾아서 호출 하였는가? (Service Discovery, REST, FeignClient)
    - 서킷브레이커를 통하여  장애를 격리시킬 수 있는가?
  - 이벤트 드리븐 아키텍처의 구현
    - 카프카를 이용하여 PubSub 으로 하나 이상의 서비스가 연동되었는가?
    - Correlation-key:  각 이벤트 건 (메시지)가 어떠한 폴리시를 처리할때 어떤 건에 연결된 처리건인지를 구별하기 위한 Correlation-key 연결을 제대로 구현 하였는가?
    - Message Consumer 마이크로서비스가 장애상황에서 수신받지 못했던 기존 이벤트들을 다시 수신받아 처리하는가?
    - Scaling-out: Message Consumer 마이크로서비스의 Replica 를 추가했을때 중복없이 이벤트를 수신할 수 있는가
    - CQRS: Materialized View 를 구현하여, 타 마이크로서비스의 데이터 원본에 접근없이(Composite 서비스나 조인SQL 등 없이) 도 내 서비스의 화면 구성과 잦은 조회가 가능한가?

- 운영
  - SLA 준수
    - 셀프힐링: Liveness Probe 를 통하여 어떠한 서비스의 health 상태가 지속적으로 저하됨에 따라 어떠한 임계치에서 pod 가 재생되는 것을 증명할 수 있는가?
    - 서킷브레이커, 레이트리밋 등을 통한 장애격리와 성능효율을 높힐 수 있는가?
    - 오토스케일러 (HPA) 를 설정하여 확장적 운영이 가능한가?
    - 모니터링, 앨럿팅: 
  - 무정지 운영 CI/CD (10)
    - Readiness Probe 의 설정과 Rolling update을 통하여 신규 버전이 완전히 서비스를 받을 수 있는 상태일때 신규버전의 서비스로 전환됨을 siege 등으로 증명 



# 분석/설계

## Event Storming 결과

![image](https://github.com/dkdpaw/sejong/blob/7f0943260bfdcfeeec7dff6f69cf7ab06019e70b/msaez_eventstoming.png)


### 1차 완성본에 대한 기능적/비기능적 요구사항을 커버하는지 검증

    - 고객이 상품을 주문한다.
    - 배송이 등록된다.
    - 상품의 재고가 감소한다.
    - 고객이 주문을 취소한다.
    - 배송이 취소된다.
    - 상품의 재고가 증가한다.


# 구현:

분석/설계 단계에서 도출된 헥사고날 아키텍처에 따라, 각 BC별로 대변되는 마이크로 서비스들을 스프링부트와 파이선으로 구현하였다. 구현한 각 서비스를 로컬에서 실행하는 방법은 아래와 같다 (각자의 포트넘버는 8081 ~ 808n 이다)

```
cd order
mvn spring-boot:run

cd delivery
mvn spring-boot:run 

cd stock
mvn spring-boot:run  

```
- 적용 후 REST API 의 테스트
```
# 상품주문 처리
gitpod /workspace/order (main) $ http localhost:8082/orders customerId=1 productId=1 productName="TV" qty=1
HTTP/1.1 201 
Connection: keep-alive
Content-Type: application/json
Date: Wed, 14 Jun 2023 14:34:34 GMT
Keep-Alive: timeout=60
Location: http://localhost:8082/orders/1
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_links": {
        "order": {
            "href": "http://localhost:8082/orders/1"
        },
        "self": {
            "href": "http://localhost:8082/orders/1"
        }
    },
    "customerId": "1",
    "productId": "1",
    "productName": "TV",
    "qty": 1
}


gitpod /workspace/order (main) $ http localhost:8082/orders
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/hal+json
Date: Wed, 14 Jun 2023 14:36:57 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_embedded": {
        "orders": [
            {
                "_links": {
                    "order": {
                        "href": "http://localhost:8082/orders/1"
                    },
                    "self": {
                        "href": "http://localhost:8082/orders/1"
                    }
                },
                "customerId": "1",
                "productId": "1",
                "productName": "TV",
                "qty": 1
            }
        ]
    },
    "_links": {
        "profile": {
            "href": "http://localhost:8082/profile/orders"
        },
        "self": {
            "href": "http://localhost:8082/orders"
        }
    },
    "page": {
        "number": 0,
        "size": 20,
        "totalElements": 1,
        "totalPages": 1
    }
}


# 배송등록 처리
gitpod /workspace/order (main) $ http localhost:8085/deliveries deliveryId=1 orderId=11 customerId=111
HTTP/1.1 201 
Connection: keep-alive
Content-Type: application/json
Date: Thu, 15 Jun 2023 13:50:52 GMT
Keep-Alive: timeout=60
Location: http://localhost:8085/deliveries/1
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_links": {
        "delivery": {
            "href": "http://localhost:8085/deliveries/1"
        },
        "self": {
            "href": "http://localhost:8085/deliveries/1"
        }
    },
    "customerId": "111",
    "orderId": 11
}


gitpod /workspace/order (main) $ http localhost:8085/deliveries
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/hal+json
Date: Thu, 15 Jun 2023 13:51:02 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_embedded": {
        "deliveries": [
            {
                "_links": {
                    "delivery": {
                        "href": "http://localhost:8085/deliveries/1"
                    },
                    "self": {
                        "href": "http://localhost:8085/deliveries/1"
                    }
                },
                "customerId": "111",
                "orderId": 11
            }
        ]
    },
    "_links": {
        "profile": {
            "href": "http://localhost:8085/profile/deliveries"
        },
        "self": {
            "href": "http://localhost:8085/deliveries"
        }
    },
    "page": {
        "number": 0,
        "size": 20,
        "totalElements": 1,
        "totalPages": 1
    }
}

# 상품재고 등록 처리
gitpod /workspace/order (main) $ http localhost:8083/stocks stockId=1 productId=1 productName="TV" stockCount=20
HTTP/1.1 201 
Connection: keep-alive
Content-Type: application/json
Date: Thu, 15 Jun 2023 13:57:15 GMT
Keep-Alive: timeout=60
Location: http://localhost:8083/stocks/1
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_links": {
        "self": {
            "href": "http://localhost:8083/stocks/1"
        },
        "stock": {
            "href": "http://localhost:8083/stocks/1"
        }
    },
    "productId": "1",
    "productName": "TV",
    "stockCount": 20
}


gitpod /workspace/order (main) $ http localhost:8083/stocks
HTTP/1.1 200 
Connection: keep-alive
Content-Type: application/hal+json
Date: Thu, 15 Jun 2023 13:57:30 GMT
Keep-Alive: timeout=60
Transfer-Encoding: chunked
Vary: Origin
Vary: Access-Control-Request-Method
Vary: Access-Control-Request-Headers

{
    "_embedded": {
        "stocks": [
            {
                "_links": {
                    "self": {
                        "href": "http://localhost:8083/stocks/1"
                    },
                    "stock": {
                        "href": "http://localhost:8083/stocks/1"
                    }
                },
                "productId": "1",
                "productName": "TV",
                "stockCount": 20
            }
        ]
    },
    "_links": {
        "profile": {
            "href": "http://localhost:8083/profile/stocks"
        },
        "self": {
            "href": "http://localhost:8083/stocks"
        }
    },
    "page": {
        "number": 0,
        "size": 20,
        "totalElements": 1,
        "totalPages": 1
    }
}

```

#이후 진행건은 msaez가 오류인건지 클래스가 생성되지 않거나 기존에 삭제한 것이 코드상에서는 없어지지 않아 빌드 시 오류가 발생하여 처리 불가 하여 이후 진행은 하지 못했습니다.


