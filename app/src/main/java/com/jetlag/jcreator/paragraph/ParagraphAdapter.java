package com.jetlag.jcreator.paragraph;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jetlag.jcreator.R;

import java.util.List;

/**
 * Created by vince on 28/08/16.
 */
public class ParagraphAdapter extends RecyclerView.Adapter<ParagraphViewHolder> {

  private final Activity activity;

  private final int resource;

  private List<Paragraph> paragraphs;

  public ParagraphAdapter(Activity activity, int resource, List<Paragraph> paragraphs) {
    this.activity = activity;
    this.resource = resource;
    this.paragraphs = paragraphs;
  }

  public void setParagraphs(List<Paragraph> paragraphs) {
    this.paragraphs = paragraphs;
  }

  @Override
  public ParagraphViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(resource, parent, false);
    TextView storyView = (TextView) v.findViewById(R.id.paragraph_text);
    return new ParagraphViewHolder(v, storyView);
  }

  @Override
  public void onBindViewHolder(ParagraphViewHolder holder, int position) {
    String s = ((TextParagraph) paragraphs.get(position)).getText();
    holder.getParagraphView().setText(s);
  }

  @Override
  public int getItemCount() {
    return paragraphs.size();
  }

}
