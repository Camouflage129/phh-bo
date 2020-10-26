kubectl apply -f storageclass.yaml
kubectl apply -f pv.yaml
kubectl apply -f pvc.yaml
kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
sleep 10 
mysql_pod_name=$(kubectl get pods --no-headers -o custom-columns=":metadata.name")
kubectl exec -it ${mysql_pod_name} -- mysql -uroot -pphh!\@\# < mysql.sql

docker run -d -p 3306:3306 camouflage/phh-mysql:5.7
container_id=$(docker ps | grep mysql | awk '{print $1}')
docker exec -i ${container_id} mysql -uroot -p'phh!@#' <./mysql.sql