package com.dingdong.core.consts;

public class StaticVal {

    /*
    토큰 관련 static 변수들
     */
    public static final String TOKEN_TYPE = "type";
    public static final String ACCESS_TOKEN = "ACCESS_TOKEN";
    public static final String REFRESH_TOKEN = "REFRESH_TOKEN";
    public static final String BEARER = "Bearer ";

    public static final Integer OK = 200;
    public static final Integer REDIRECTION = 300;
    /*
    에러 코드 관련 static 변수들
     */
    public static final Integer BAD_REQUEST = 400;
    public static final Integer UNAUTHORIZED = 401;
    public static final Integer FORBIDDEN = 403;
    public static final Integer NOT_FOUND = 404;
    public static final Integer SERVER_ERROR = 500;

    /*
    빌드 환경 관련 static 변수들
     */
    public static final String DEV = "dev";
    public static final String PROD = "prod";

    /*
    스웨거 패턴
     */
    public static final String[] SwaggerPatterns = {
        "/swagger-resources/**",
        "/swagger-ui/**",
        "/v3/api-docs/**",
        "/v3/api-docs",
        "/swagger-ui.html",
    };

    /** 행성 디폴트 커버 이미지/소개 */
    public static final String COMMUNITY_DEFAULT_IMAGE =
            "https://depromeet-image-bucket.s3.ap-northeast-2.amazonaws.com/085363b2-53e0-490c-9967-e6534cf4ce16.png";

    public static final String COMMUNITY_DEFAULT_DESCRIPTION = "우리 행성에 온 걸 환영해!";
}
