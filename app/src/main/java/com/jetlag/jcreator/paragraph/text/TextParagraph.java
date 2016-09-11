package com.jetlag.jcreator.paragraph.text;

import com.jetlag.jcreator.paragraph.Paragraph;

/**
 * Created by vince on 09/09/16.
 */
public class TextParagraph implements Paragraph {

  private final String textContent;

  public TextParagraph(String textContent) {
    this.textContent = textContent;
  }

  public String getText() {
    return textContent;
  }
}
