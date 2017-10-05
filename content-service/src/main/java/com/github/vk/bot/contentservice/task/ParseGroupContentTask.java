package com.github.vk.bot.contentservice.task;

import com.github.vk.bot.common.model.content.ContentSource;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.OAuthException;
import com.vk.api.sdk.objects.UserAuthResponse;
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

    public ParseGroupContentTask(List<ContentSource> allBySourceType, VkApiClient vkApiClient) {
        this.allBySourceType = allBySourceType;
        this.vkApiClient = vkApiClient;
    }

    @Override
    protected void compute() {
        if (allBySourceType == null || allBySourceType.isEmpty()) {
            LOG.info("-- Empty content source");
            return;
        }
        if (allBySourceType.size() > 1) {
            LOG.info("-- Content source > 1 " + Thread.currentThread().getName());
            ParseGroupContentTask subTask = new ParseGroupContentTask(allBySourceType.subList(1, allBySourceType.size()), vkApiClient);
            subTask.fork();
        }
        process(allBySourceType.iterator().next());
    }

    private void process(ContentSource contentSource) {
        LOG.info("-- Start processing content source " + Thread.currentThread().getName() + " " + contentSource);
        try {
            UserAuthResponse authResponse = vkApiClient.oauth()
                    .userAuthorizationCodeFlow(APP_ID, CLIENT_SECRET, REDIRECT_URI, code)
                    .execute();
            UserActor actor = new UserActor(authResponse.getUserId(), authResponse.getAccessToken());
        } catch (OAuthException e) {
            e.getRedirectUri();
        }
    }

}
