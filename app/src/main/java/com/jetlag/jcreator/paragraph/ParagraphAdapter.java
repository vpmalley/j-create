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

  private List<String> paragraphs;

  public ParagraphAdapter(Activity activity, int resource, List<String> paragraphs) {
    this.activity = activity;
    this.resource = resource;
    this.paragraphs = paragraphs;
  }

  public void setParagraphs(List<String> paragraphs) {
    this.paragraphs = paragraphs;
  }

  @Override
  public ParagraphViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View v = LayoutInflater.from(parent.getContext())
        .inflate(resource, parent, false);
    TextView storyView = (TextView) v.findViewById(R.id.paragraphs);
    return new ParagraphViewHolder(v, storyView);
  }

  @Override
  public void onBindViewHolder(ParagraphViewHolder holder, int position) {
    String s = paragraphs.get(position);
    holder.getParagraphView().setText(s);
  }

  @Override
  public int getItemCount() {
    return paragraphs.size();
  }


}
