package com.github.vk.bot.groupservice.task;

import com.github.vk.bot.common.client.AccountClient;
import com.github.vk.bot.common.client.ContentSourceClient;
import com.github.vk.bot.common.model.account.Account;
import com.github.vk.bot.common.model.group.Group;
import com.vk.api.sdk.client.VkApiClient;
import com.vk.api.sdk.client.actors.UserActor;
import com.vk.api.sdk.exceptions.ApiException;
import com.vk.api.sdk.exceptions.ClientException;
import com.vk.api.sdk.objects.photos.Photo;
import com.vk.api.sdk.objects.photos.PhotoUpload;
import com.vk.api.sdk.objects.photos.responses.WallUploadResponse;
import com.vk.api.sdk.objects.wall.responses.PostResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.RecursiveAction;

/**
 * Created at 16.10.2017 15:57
 *
 * @author AMarchenkov
 */
@Slf4j
public class VkPostTask extends RecursiveAction {

    private final transient VkApiClient vkApiClient;
    private final transient Group group;
    private final transient AccountClient accountClient;
    private final transient ContentSourceClient contentSourceClient;

    public VkPostTask(VkApiClient vkApiClient, Group group, AccountClient accountClient, ContentSourceClient contentSourceClient) {
        this.vkApiClient = vkApiClient;
        this.group = group;
        this.accountClient = accountClient;
        this.contentSourceClient = contentSourceClient;
    }

    @Override
    protected void compute() {
        List<Account> actualAccounts = accountClient.getActualAccounts();
        if (actualAccounts != null && !actualAccounts.isEmpty()) {
            Account account = actualAccounts.get(0);
            UserActor userActor = new UserActor(account.getUserId(), account.getAccessToken());

            contentSourceClient.getAllContentItems().parallelStream().forEach(item -> {
                if (item.getAttachments() == null || item.getAttachments().isEmpty()) {
                    return;
                }
                try {
                    List<String> photoToPost = new ArrayList<>();
                    item.getAttachments().forEach(attachment -> {
                        if (attachment.getPhoto() == null) {
                            return;
                        }
                        if (attachment.getPhoto().getPhoto2560() != null) {
                            photoToPost.add(attachment.getPhoto().getPhoto2560());
                            return;
                        }
                        if (attachment.getPhoto().getPhoto1280() != null) {
                            photoToPost.add(attachment.getPhoto().getPhoto1280());
                            return;
                        }
                        if (attachment.getPhoto().getPhoto807() != null) {
                            photoToPost.add(attachment.getPhoto().getPhoto807());
                            return;
                        }
                        if (attachment.getPhoto().getPhoto604() != null) {
                            photoToPost.add(attachment.getPhoto().getPhoto604());
                            return;
                        }
                        if (attachment.getPhoto().getPhoto130() != null) {
                            photoToPost.add(attachment.getPhoto().getPhoto130());
                            return;
                        }
                        if (attachment.getPhoto().getPhoto75() != null) {
                            photoToPost.add(attachment.getPhoto().getPhoto75());
                        }
                    });
                    photoToPost.forEach(photoLink -> {
                        URL url;
                        try {
                            url = new URL(photoLink);
                        } catch (MalformedURLException e) {
                            LOG.error("Incorrect url", e);
                            return;
                        }
                        try (ReadableByteChannel channel = Channels.newChannel(url.openStream());
                             FileOutputStream fileOutputStream = new FileOutputStream(photoLink)) {
                            fileOutputStream.getChannel().transferFrom(channel, 0, Long.MAX_VALUE);
                        } catch (IOException e) {
                            LOG.error("Saving photo failed", e);
                        }
                    });

                    PhotoUpload photoUpload = vkApiClient.photos().getWallUploadServer(userActor)
                            .groupId(group.getGroupId())
                            .execute();
                    WallUploadResponse uploadResponse = vkApiClient.upload()
                            .photoWall(photoUpload.getUploadUrl(), new File(photoToPost.get(0)))
                            .execute();

                    List<Photo> photos = vkApiClient.photos().saveWallPhoto(userActor, uploadResponse.getPhoto())
                            .groupId(group.getGroupId())
                            .hash(uploadResponse.getHash())
                            .server(uploadResponse.getServer()).execute();
                    if (photos.isEmpty()) {
                        LOG.error("File upload failed");
                    }

                    PostResponse postResponse = vkApiClient.wall()
                            .post(userActor)
                            .message("test")
                            .attachments("photo" + photos.get(0).getOwnerId() + "_" + photos.get(0).getId())
                            .ownerId(group.getGroupId() * -1)
                            .execute();
                    if (postResponse.getPostId() == null || postResponse.getPostId() < 0) {
                        LOG.error("Post failed");
                    } else {
                        LOG.info("Successfully posted");
                    }
                    Thread.sleep(1234);
                } catch (ApiException | ClientException e) {
                    LOG.error("API exception", e);
                } catch (InterruptedException e) {
                    LOG.error("Thread interrupt", e);
                }
            });
        } else {
            LOG.info("NO actual accounts");
        }
    }

}
