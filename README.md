저는 DevOps Engineer가 되고자 하는 초보 엔지니어입니다.  
개발에 대해 잘 모르지만, EKS 세팅 후 테스트할 Spring Boot Application이 필요했습니다.  
프로젝트를 수행하며 해당 예시를 만들게 되었고, 이를 공유하고자 합니다.  

Spring Boot에서 Readiness Probe와 Liveness Probe 설정과 같은 부분을 체크하기 위한 테스트 어플리케이션 입니다.  
해당 설정들을 알맞게 수정하면서 원하는 형태의 Pod LifeCycle을 만들어 테스트 해보실 수 있습니다.  

또한 ConfigMap / Secret을 Spring Boot에서 매핑하는 방법, DB연동에 대한 체크 부분 등을  
해당 소스를 가지고 바로 올려서 작동 방식에 대해서 이해하고, 체크해 볼 수 있습니다.  

사용 방법  
1. Configmap 세팅
   - db-host: 의 값을 RDS나 Spring Boot에 연결 할 DB Host 세팅 값을 넣어주시면 됩니다.
   - fluentd-host: WAS에서 바로 FluentD로 was로그를 쏠 수 있도록 세팅되어 있습니다.  
     Docs에 있는 24224 포트를 사용합니다.
  
2. Secret 세팅
   - DB User / PW에 대해서 Base64 인코딩 값을 넣어주어야 합니다.  
     작성된 예시에서는 phh / phh!@#의 값이 들어가 있습니다.
  
3. Deployment.yaml / application.yaml  
   - LivenessProbe / ReadinessProbe의 주기적인 체크 시간(periodSeconds) / 실패 횟수(failureThreshold) 등을 조절하여  
     알맞게 Pod LifeCycle을 조절할 수 있습니다.  
     API이기 때문에 아무곳에서나 콜이 되면 안되므로, 해더에 x-k8s-key라는 값을 통해 조작 가능하게 하였습니다.  
   - application.yaml에서 lifecylce.timeout-per-shutdown-phase에서 WAS를 강제 종료시키는 timeout 시간을 정할 수 있습니다.  

4. mysql
   - RDS나 다른 DB를 구성하여서 사용하셔도 무방하며, mysql.sql을 통해 해당 BO와 연동되는 DB만 알맞게 세팅해주시면 됩니다.

5. skaffold.yaml / pom.xml
   - {{dev-account-ecr-url}} / {{stg-account-ecr-url}} / {{prd-account-ecr-url}}
     위의 값에 각 ECR의 URL을 입력해주시면 됩니다. Tag는 넣지 않고 빌드 시 아래 예처럼 넣어주시면 됩니다.  

     -- Tag 빌드 예시 --  
     sh "VER=${TAG} skaffold build -p ${CONFIG_ENV} --cache-artifacts=false" 와 같이 넣으면 됩니다.  

   - Docker Base Image는 pom.xml의 profile란에 입력합니다.  
     digest 값이 아니면 정확하게 가져오지 못하므로 CICD 구성시  
     digest를 항상 최신값을 가져올 수 있도록 아래 예 처럼 구성해야합니다.  

     -- Jenkins Pipeline Script 예시 --  
     def cmd = "aws ecr list-images --repository-name ${base-ecr-img-name} --output text --query \"imageIds[?imageTag=='latest'].imageDigest\""  
     def digest =  sh(returnStdout: true, script: cmd).trim()  
     sh("sed -i 's!<digest>.*!<digest>${digest}</digest>!g' /var/lib/jenkins/workspace/${JOB_NAME}/pom.xml")

   - pom.xml 가장 하단에 base image 세팅을 직접 하실 경우 주석을 푸시고  
     camouflage/base:0.1 부분을 삭제해주시면 됩니다.

6. 소스코드 수정 하여 좀 더 커스터마이징 하고 싶으실 경우,  
   mvn build를 아래와 같이 하셔서 yaml 세팅과 맞게 빌드하신 후 사용하시면 됩니다.  
   - application-환경.yaml  
     -- 빌드 예시 --  
     mvn clean package -DskipTests -Djib.container.environment=SPRING_PROFILES_ACTIVE=환경  
     docker build -t tag값 .

* 해당 관련 구체적인 활용 방안 및 예시는 나머지 필요한 소스들을 모두 완성한 후 블로그를 통해 캡처본으로 게시할 예정입니다.
  https://aws-diary.tistory.com