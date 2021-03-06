package com.jetlag.jcreator.paragraph;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.jetlag.jcreator.paragraph.picture.DevicePictureParagraph;
import com.jetlag.jcreator.paragraph.picture.PictureParagraphAdapter;
import com.jetlag.jcreator.paragraph.picture.PictureParagraphViewHolder;
import com.jetlag.jcreator.paragraph.text.TextParagraph;
import com.jetlag.jcreator.paragraph.text.TextParagraphAdapter;
import com.jetlag.jcreator.paragraph.text.TextParagraphViewHolder;

import java.util.List;

/**
 * Created by vince on 28/08/16.
 */
public class ParagraphAdapter extends RecyclerView.Adapter<ParagraphViewHolder> {

  public static final int TYPE_UNKNOWN = 0;
  public static final int TYPE_TEXT = 1;
  public static final int TYPE_PICTURE = 2;

  private final Activity activity;
  private List<Paragraph> paragraphs;

  private final TextParagraphAdapter textParagraphAdapter;
  private final PictureParagraphAdapter pictureParagraphAdapter;

  public ParagraphAdapter(Activity activity, List<Paragraph> paragraphs) {
    this.activity = activity;
    this.paragraphs = paragraphs;
    this.textParagraphAdapter = new TextParagraphAdapter();
    this.pictureParagraphAdapter = new PictureParagraphAdapter(activity);
  }

  public void setParagraphs(List<Paragraph> paragraphs) {
    this.paragraphs = paragraphs;
  }

  @Override
  public ParagraphViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case TYPE_PICTURE:
        return pictureParagraphAdapter.onCreateViewHolder(parent);
      case TYPE_TEXT:
      default:
        return textParagraphAdapter.onCreateViewHolder(parent);
    }
  }

  @Override
  public void onBindViewHolder(ParagraphViewHolder holder, int position) {
    switch (getItemViewType(position)) {
      case TYPE_PICTURE:
        pictureParagraphAdapter.onBindViewHolder((PictureParagraphViewHolder) holder, (DevicePictureParagraph) paragraphs.get(position));
        break;
      case TYPE_TEXT:
      default:
        textParagraphAdapter.onBindViewHolder((TextParagraphViewHolder) holder, (TextParagraph) paragraphs.get(position));
        break;
    }
  }

  @Override
  public int getItemCount() {
    return paragraphs.size();
  }

  @Override
  public int getItemViewType(int position) {
    Paragraph p = paragraphs.get(position);
    if (p instanceof TextParagraph) {
      return TYPE_TEXT;
    } else if (p instanceof DevicePictureParagraph) {
      return TYPE_PICTURE;
    } else {
      return TYPE_UNKNOWN;
    }
  }
}
