FROM nginx:alpine

# Nginx 설정 디렉토리에 default.conf 파일 복사
RUN mkdir -p /etc/nginx/conf.d
COPY default.conf /etc/nginx/conf.d/default.conf

# 설정 파일이 제대로 복사되었는지 확인 (디버깅용)
RUN ls -la /etc/nginx/conf.d/
