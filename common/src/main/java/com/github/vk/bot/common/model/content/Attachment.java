package com.github.vk.bot.common.model.content;

import com.github.vk.bot.common.enums.AttachmentType;
import com.google.gson.annotations.SerializedName;
import lombok.Data;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.io.Serializable;

/**
 * Created at 28.09.2017 15:54
 *
 * @author AMarchenkov
 */
@Data
@Document(collection = Attachment.COLLECTION_NAME)
public class Attachment implements Serializable {

    public static final String COLLECTION_NAME = "attachments";

    @Id
    private ObjectId id;

    @Field("attachment_type")
    @SerializedName("attachment_type")
    private AttachmentType attachmentType;

    @Field
    private Photo photo;
}
