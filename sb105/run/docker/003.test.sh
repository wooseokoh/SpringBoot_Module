curl --header "Contect-Type: application/json" \
--request POST \
--data '{"productId":9,"productName":9_name,"productInfo":9_productInfo,"recommendList":[{"recommendId":91,"author":"Author91","content":"Content91"}],"reviewList":[{"reviewId":1,"author":"Author1","subject":"Subject1","content":"Content1"}]}' \
http://192.168.100.100:8080/composite

curl http://localhost:8080/composite/9 | jq

curl -X "DELETE" http://localhost:8080/composite/9