1. Check the security group of the cloud server and open the corresponding port.

15432 postgres
16379 redis
18001 eureka server
19999 gateway
# 17911 iot-message
# 17912 iot-transit
# 17913 iot-beacon
# 17914 iot-gps
# 17915 iot-config-center

80 SSL Certificate

2. Use ufw to check the port settings of the server.

sudo ufw allow 15432/tcp \
sudo ufw allow 18001/tcp \
sudo ufw allow 19999/tcp \
# sudo ufw allow 17911/tcp \
# sudo ufw allow 17912/tcp \
# sudo ufw allow 17913/tcp \
# sudo ufw allow 17914/tcp \
# sudo ufw allow 17915/tcp

3. Check nginx/conf.d/iot.conf is uploaded
4. Check .env is uploaded
5. Check docker-compose.yml is uploaded
6. Check remote-deploy.sh is uploaded