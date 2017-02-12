package com.jetlag.jcreator.paragraph.picture;

import com.jetlag.jcreator.paragraph.markdown.MarkdownParagraph;
import com.jetlag.jcreator.pictures.DevicePicture;

/**
 * Created by vince on 11/02/17.
 */
public class PictureParagraphToMarkdown {

    public MarkdownParagraph formatParagraphToMarkdown(DevicePictureParagraph paragraph) {
        StringBuilder markdownContentBuilder = new StringBuilder();

        for (DevicePicture picture : paragraph.getDevicePictures()) {

        }
        return new MarkdownParagraph(markdownContentBuilder.toString());
    }

}
