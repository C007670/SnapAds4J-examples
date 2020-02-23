/*
 * Copyright 2020 Yassine AZIMANI
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package snapads4j.examples.authentication;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import snapads4j.auth.SnapAuthorization;
import snapads4j.exceptions.SnapAuthorizationException;
import snapads4j.model.auth.TokenResponse;

import java.io.IOException;

@RestController
public class EndPoints {

    /**
     * Step 1 : Get Authorization Form URI
     */
    @GetMapping("/getOAuthAuthorizationURI")
    public ResponseEntity<?> getOAuthAuthorizationURI(){
        try {
            SnapAuthorization auth = new SnapAuthorization();
            String url = auth.getOAuthAuthorizationURI();
            return new ResponseEntity<>(url, HttpStatus.OK);
        } catch (SnapAuthorizationException | IOException e) {
            e.printStackTrace();
            return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }// getOAuthAuthorizationURI()

    /**
     * Step 2 : Get Code to generate Token
     * @param code Code
     * @param state State (Optionnal)
     */
    @GetMapping("/getOAuthAccessToken")
    public ResponseEntity<?> getOAuthAccessToken(@RequestParam("code") String code, @RequestParam("state") String state){
        if(code != null) {
            TokenResponse tokenResponse;
            try {
                SnapAuthorization auth = new SnapAuthorization();
                tokenResponse = auth.getOAuthAccessToken(code);
                System.out.println("Access Token : " + tokenResponse.getAccessToken());
                // Let's save the access token in a cookie/session/database :
                saveMyAccessToken(tokenResponse.getAccessToken());
                return new ResponseEntity<>("OK", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
                return new ResponseEntity<>("Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }// getOAuthAccessToken()

    /**
     * Step 3 (optionnal) : Refresh Token
     * @param token old token
     */
    @GetMapping("/refreshToken")
    public ResponseEntity<?> refreshToken(@RequestParam("token") String token){
        if(token != null) {
            try {
                SnapAuthorization auth = new SnapAuthorization();
                TokenResponse tokenResponse = auth.refreshToken(token);
                String accessToken = tokenResponse.getAccessToken();
                // Let's save the access token in a cookie/session/database :
                saveMyAccessToken(accessToken);
                return new ResponseEntity<>("OK", HttpStatus.OK);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return new ResponseEntity<>("Bad request", HttpStatus.BAD_REQUEST);
    }// refreshToken()

    private void saveMyAccessToken(String token){
        // Save token (Database, session, cookie...)
    }// saveMyAccessToken()

}// EndPoints
