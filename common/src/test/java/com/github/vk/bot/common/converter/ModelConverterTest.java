package com.github.vk.bot.common.converter;

import com.github.vk.bot.common.model.content.Item;
import com.github.vk.bot.common.model.user.GroupUser;
import com.github.vk.bot.common.test.TestUtils;
import com.google.gson.Gson;
import com.vk.api.sdk.objects.users.UserXtrCounters;
import com.vk.api.sdk.objects.wall.WallPostFull;
import org.junit.Test;

import java.io.IOException;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

/**
 * Created at 17.10.2017 14:13
 *
 * @author AMarchenkov
 */
public class ModelConverterTest {

    @Test
    public void shouldReturnMongoUserEntity() throws IOException {
        ModelConverter converter = new ModelConverter();
        Gson gson = new Gson();
        UserXtrCounters vkUser = gson.fromJson(TestUtils.getResourceAsString("vk_user.json"), UserXtrCounters.class);
        GroupUser groupUser = converter.convertToMongoUser(vkUser);

        assertThat(groupUser.getCountry(), is(equalTo(vkUser.getCountry().getTitle())));
        assertThat(groupUser.getContentSourceFrom(), is(equalTo(null)));
        assertThat(groupUser.getFirstName(), is(equalTo(vkUser.getFirstName())));
        assertThat(groupUser.getLastName(), is(equalTo(vkUser.getLastName())));
        assertThat(groupUser.getId(), is(equalTo(null)));
        assertThat(groupUser.getSourceUserId(), is(equalTo(vkUser.getId())));
        assertThat(groupUser.getState(), is(equalTo(vkUser.getCity().getTitle())));
        assertThat(groupUser.getSourceType(), is(equalTo(null)));
        assertThat(groupUser.getGender().toString(), is(equalTo(vkUser.getSex().toString())));
    }

    @Test
    public void shouldReturnMongoContentItemFromVkResponse() throws IOException {
        ModelConverter converter = new ModelConverter();
        Gson gson = new Gson();
        WallPostFull wallPostFull = gson.fromJson(TestUtils.getResourceAsString("vk_wall.json"), WallPostFull.class);
        Item item = converter.fromVkItemToMongoItem(wallPostFull);

        assertThat(item.getContentSourceId(), is(equalTo(null)));
        assertThat((int) item.getDate(), is(equalTo(wallPostFull.getDate())));
        assertThat(item.getId(), is(equalTo(null)));
        assertThat(item.getText(), is(equalTo(wallPostFull.getText())));
        assertThat((int) item.getSourceId(), is(equalTo(wallPostFull.getId())));
        assertThat(item.getAttachments().size(), is(equalTo(wallPostFull.getAttachments().size())));
        assertThat(item.getPostType().toString(), is(equalTo(wallPostFull.getPostType().toString())));

        assertThat(item.getAttachments().get(0).getAttachmentType().toString(), is(equalTo(wallPostFull.getAttachments().get(0).getType().toString())));
        assertThat((int) item.getAttachments().get(0).getPhoto().getDate(), is(equalTo(wallPostFull.getAttachments().get(0).getPhoto().getDate())));
        assertThat(item.getAttachments().get(0).getPhoto().getHeight(), is(equalTo(wallPostFull.getAttachments().get(0).getPhoto().getHeight())));
        assertThat((int) item.getAttachments().get(0).getPhoto().getOwnerId(), is(equalTo(wallPostFull.getAttachments().get(0).getPhoto().getOwnerId())));
        assertThat(item.getAttachments().get(0).getPhoto().getPhoto75(), is(equalTo(wallPostFull.getAttachments().get(0).getPhoto().getPhoto75())));
        assertThat(item.getAttachments().get(0).getPhoto().getPhoto130(), is(equalTo(wallPostFull.getAttachments().get(0).getPhoto().getPhoto130())));
        assertThat(item.getAttachments().get(0).getPhoto().getWidth(), is(equalTo(wallPostFull.getAttachments().get(0).getPhoto().getWidth())));
        assertThat(item.getAttachments().get(0).getPhoto().getUserId(), is(equalTo(wallPostFull.getAttachments().get(0).getPhoto().getUserId())));
        assertThat(item.getAttachments().get(0).getPhoto().getText(), is(equalTo(wallPostFull.getAttachments().get(0).getPhoto().getText())));
        assertThat((int) item.getAttachments().get(0).getPhoto().getSourceId(), is(equalTo(wallPostFull.getAttachments().get(0).getPhoto().getId())));
        assertThat(item.getAttachments().get(0).getPhoto().getPhoto2560(), is(equalTo(wallPostFull.getAttachments().get(0).getPhoto().getPhoto2560())));
        assertThat(item.getAttachments().get(0).getPhoto().getPhoto1280(), is(equalTo(wallPostFull.getAttachments().get(0).getPhoto().getPhoto1280())));
        assertThat(item.getAttachments().get(0).getPhoto().getPhoto807(), is(equalTo(wallPostFull.getAttachments().get(0).getPhoto().getPhoto807())));
        assertThat(item.getAttachments().get(0).getPhoto().getPhoto604(), is(equalTo(wallPostFull.getAttachments().get(0).getPhoto().getPhoto604())));
    }
}
