package com.github.vk.bot.contentservice.task;

import com.github.vk.bot.common.model.account.Account;
import com.github.vk.bot.common.model.content.ContentSource;
import com.github.vk.bot.common.model.content.Item;
import com.github.vk.bot.contentservice.client.AccountClient;
import com.github.vk.bot.contentservice.repository.ContentSourceRepository;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.exceptions.OAuthException;
import com.vk.api.sdk.objects.UserAuthResponse;
import com.vk.api.sdk.objects.wall.responses.GetResponse;
import lombok.extern.apachecommons.CommonsLog;

import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created at 05.10.2017 14:24
 *
 * @author AMarchenkov
 */
@CommonsLog
public class ParseGroupContentTask extends RecursiveAction {

    private List<ContentSource> allBySourceType;
    private VkApiClient vkApiClient;
    private int clientId;
    private String clientSecret;
    private String redirectUrl;
    private AccountClient accountClient;
    private ContentSourceRepository contentSourceRepository;

    public ParseGroupContentTask(List<ContentSource> allBySourceType, VkApiClient vkApiClient, int clientId,
                                 String clientSecret, String redirectUrl, AccountClient accountClient) {
        this.allBySourceType = allBySourceType;
        this.vkApiClient = vkApiClient;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.redirectUrl = redirectUrl;
        this.accountClient = accountClient;
    }

    @Override
    protected void compute() {
        if (allBySourceType == null || allBySourceType.isEmpty()) {
            LOG.debug("Nothing to get. No sources detected");
            return;
        }
        if (allBySourceType.size() > 1) {
            LOG.debug("Fork task. Too much sources");
            ParseGroupContentTask subTask = new ParseGroupContentTask(allBySourceType.subList(1, allBySourceType.size()),
                    vkApiClient, clientId, clientSecret, redirectUrl, accountClient);
            subTask.fork();
        }
        process(allBySourceType.iterator().next());
    }

    private void process(ContentSource contentSource) {
        Account account = accountClient.getAllAccounts().get(0);
        LOG.debug("Start processing content source " + contentSource);
        try {
            UserAuthResponse authResponse = vkApiClient
                    .oauth()
                    .userAuthorizationCodeFlow(clientId, clientSecret, redirectUrl, account.getAccessToken().getToken())
                    .execute();
            UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
            GetResponse execute = vkApiClient.wall().get(actor).ownerId(contentSource.getSourceId()).count(100).execute();
            execute.getItems().stream().filter(item -> true).forEach(wallPostFull -> {
                Item item = new Item();
                contentSource.getItems().add(item);
                contentSourceRepository.save(contentSource);
            });
        } catch (OAuthException e) {
            e.getRedirectUri();
        } catch (ApiException | ClientException e) {
            e.printStackTrace();
        }
    }

}
