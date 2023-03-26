docker build -t toodle_server_springboot:latest .
docker run -p 8080:8080 -e USE_PROFILE=local  toodle_server_springboot:latest
