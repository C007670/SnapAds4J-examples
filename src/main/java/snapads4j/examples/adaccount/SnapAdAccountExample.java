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
package snapads4j.examples.adaccount;

import snapads4j.adaccount.SnapAdAccount;
import snapads4j.enums.AdAccountTypeEnum;
import snapads4j.enums.CurrencyEnum;
import snapads4j.enums.StatusEnum;
import snapads4j.exceptions.SnapArgumentException;
import snapads4j.exceptions.SnapExecutionException;
import snapads4j.exceptions.SnapOAuthAccessTokenException;
import snapads4j.exceptions.SnapResponseErrorException;
import snapads4j.model.Pagination;
import snapads4j.model.adaccount.AdAccount;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static snapads4j.examples.Properties.*;

public class SnapAdAccountExample {

    public static void getAllAdAccountsExample(){
        try {
            SnapAdAccount sad = new SnapAdAccount();
            List<Pagination<AdAccount>> allAdAccounts = sad.getAllAdAccounts(O_AUTH_ACCESS_TOKEN, ORGANIZATION_ID, 100);
            allAdAccounts.forEach(pageAdAccount -> {
                System.out.println("Page n° : " + pageAdAccount.getNumberPage());
                System.out.println("Page size : " + pageAdAccount.getResults().size());
                pageAdAccount.getResults().forEach(System.out::println);
            });
        } catch (SnapResponseErrorException | SnapOAuthAccessTokenException | SnapArgumentException | SnapExecutionException e) {
            e.printStackTrace();
        } catch(IOException ie){
            ie.printStackTrace();
        }
    }// getAllAdAccountsExample()

    public static void getSpecificAdAccountExample(){
        try {
            SnapAdAccount sad = new SnapAdAccount();
            Optional<AdAccount> specificAdAccount = sad.getSpecificAdAccount(O_AUTH_ACCESS_TOKEN, "1ea");
            specificAdAccount.ifPresent(System.out::println);
        } catch (SnapResponseErrorException | SnapOAuthAccessTokenException | SnapArgumentException | SnapExecutionException e) {
            e.printStackTrace();
        } catch(IOException ie){
            ie.printStackTrace();
        }
    }// getSpecificAdAccountExample()

    public static void updateAdAccountExample(){
        try {
            SnapAdAccount sad = new SnapAdAccount();
            AdAccount adAccount = new AdAccount();
            adAccount.setId("1ea");
            adAccount.setStatus(StatusEnum.ACTIVE);
            adAccount.setAdvertiser("Advertiser");
            adAccount.setCurrency(CurrencyEnum.EUR);
            adAccount.setName("Name");
            adAccount.setOrganizationId(ORGANIZATION_ID);
            adAccount.setType(AdAccountTypeEnum.PARTNER);
            adAccount.setFundingSourceIds(Collections.singletonList("f4589Ko"));
            Optional<AdAccount> specificAdAccount = sad.updateAdAccount(O_AUTH_ACCESS_TOKEN, adAccount);
            specificAdAccount.ifPresent(a -> System.out.println("Update succeeded"));
        } catch (SnapResponseErrorException | SnapOAuthAccessTokenException | SnapArgumentException | SnapExecutionException e) {
            e.printStackTrace();
        } catch(IOException ie){
            ie.printStackTrace();
        }
    }// updateAdAccountExample()

    public static void main(String[] args) {
        getAllAdAccountsExample();
        getSpecificAdAccountExample();
        updateAdAccountExample();
    }// main()
}// SnapAdAccountExample
