package com.rodarte.security;

public class JwtConfig {

    public static final String SECRET_KEY = "super-token-secret";

    public static final String RSA_PRIVATE = "-----BEGIN RSA PRIVATE KEY-----\n" +
            "MIIEpAIBAAKCAQEA0pqc6mDS8UxTRANxinXLe8KAfys+omAiGInHsk2iO7xCvHMF\n" +
            "S9btxUGdIur+KV8XxJPYQXD+G6s8GMIcjPYfKZYA8xwWB43CZKOkhhwgQL5L405T\n" +
            "wWIB1o5GiR6HSDBOgQbxaHYoxCHwFtY8jRznkhaF43Vg19/K4Oz2S0pYkhgWLd2C\n" +
            "1rTpgTGIOGUbYH6n+JNctnQvxQxd4bReYRAYL+pVM2kWv+fu3UdRDcSj9ihzEsZ2\n" +
            "B/DQxUyJY3weqMx/zgWjk7Sa0WNObXyRb9BWgemGPN/s3PHDZDEw8yxIKiiW7Q41\n" +
            "fJWZeXXlUGynWuo6fmy9X3dJ15Nqj6Rvu5AzAwIDAQABAoIBACsS7F0TiJFJpw4W\n" +
            "FjbXeNLLKTTZR2o2d0LFi05wimnlS1D3xXGJeAAMN3NY1LrpdBcElgpsbcyC7Tuz\n" +
            "F9vMyvrabz9J00egOs4SmifDwOT9BtBqyL6YqP+VAeAxmkGtf6DsJpvS7uuOdWh2\n" +
            "UNQbwVEjwiaPb0s3vmrajFVB03OtknxowlRV594l5YXfweIWpB05gc2hMHRODCb+\n" +
            "2+UNR0D7+vZXxHoHRQTTPr/iNthtNPv2PI1OGXGp2yFs+IqVkVjYHkdEbNi0QuPh\n" +
            "Mnb41BRadFUuEfDmc4pNObtcjlUhKmdLcYjbFPRviX2sFrJ9GDnjTwBZO07dDgRV\n" +
            "tulY6KECgYEA7IGJaFp6YJJ/H6nOqo5lYfhXnYdzQMsfRlh4cuPCdnop4FSz8G4W\n" +
            "okbIVScDkf2QFmv1ym3DJ57PZDmosDyp9ECMZuf5Q4SOplVVPNSZBqzgfImidEvf\n" +
            "4IJ0ZAKwXAh0lXgKN/bJ1tJbfLj3jpPBGGt2q0MZw1bCfd7BOF+rbKcCgYEA4/aF\n" +
            "yMxJRne3TdK1J/sc5fmOWTZu5xQ8E2QMbF6leu83LPAS1SdYxBFpJKL5KyGKvJTg\n" +
            "jPoA6sc8zim/YNUjT3XDW2S1iGaN/7eDHNWr/Du9Avv/ryHoUaPrUxP2z0xq54zk\n" +
            "WM8fm4wz40JStkFllg+vY2bYmr7+qx4PsU2wBkUCgYEAt6F4U/r0S+lTJP2OhmTb\n" +
            "mO7e8GQL+8H6vX7RvrrS8XS28QQzVjnT/xmgUGVjY0YSSyZ2CD+jKC8pbfNK49Om\n" +
            "TgyUNw5kPqU8sS9OaFXVnxY5kti2tz92S3Ze8owX7M1v3+H9ZSiL3O0ltbhUFnLo\n" +
            "mZJO6HjIFyBVELAVLDUz/78CgYEA1rXYFmOLVKrnZI5kgC69bkJZ998UH+PoHZ9r\n" +
            "MBsJI7vyLQpFwqwXqDx52r8BjsU95hcXVNEYUgmsVMXvcmXtpybVBNzbOojVkBgp\n" +
            "jtwV7DHb+daeYTa/mT2aJRq1qVRhkIwceyVCsQAh0xghzXXVF7zYRfiVZVMljMh4\n" +
            "oVf5xj0CgYBnoFC4F1Cbs3lPqEyuuPMsUnOFmRUqmFQk23K8kmm8J4Hed0kV8UCs\n" +
            "pkU8fY5xeVtHZ6n8MyVL+wURkNPYGieGbdCNo+EgM3ovPj/22Ev1lrZLTE1XI++k\n" +
            "Abq+ZgfYMjD1+dX1D8pB1o1emDx+I6HL1d8laEi3sRyB9OlO1HzpxQ==\n" +
            "-----END RSA PRIVATE KEY-----";

    public static final String RSA_PUBLIC = "-----BEGIN PUBLIC KEY-----\n" +
            "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEA0pqc6mDS8UxTRANxinXL\n" +
            "e8KAfys+omAiGInHsk2iO7xCvHMFS9btxUGdIur+KV8XxJPYQXD+G6s8GMIcjPYf\n" +
            "KZYA8xwWB43CZKOkhhwgQL5L405TwWIB1o5GiR6HSDBOgQbxaHYoxCHwFtY8jRzn\n" +
            "khaF43Vg19/K4Oz2S0pYkhgWLd2C1rTpgTGIOGUbYH6n+JNctnQvxQxd4bReYRAY\n" +
            "L+pVM2kWv+fu3UdRDcSj9ihzEsZ2B/DQxUyJY3weqMx/zgWjk7Sa0WNObXyRb9BW\n" +
            "gemGPN/s3PHDZDEw8yxIKiiW7Q41fJWZeXXlUGynWuo6fmy9X3dJ15Nqj6Rvu5Az\n" +
            "AwIDAQAB\n" +
            "-----END PUBLIC KEY-----";

}
