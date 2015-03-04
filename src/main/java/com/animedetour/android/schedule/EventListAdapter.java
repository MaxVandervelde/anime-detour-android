package com.animedetour.android.schedule;

import android.widget.SectionIndexer;
import com.animedetour.api.sched.api.model.Event;
import com.inkapplications.android.widget.listview.ItemAdapter;
import com.inkapplications.android.widget.recyclerview.ItemViewBinder;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class EventListAdapter extends ItemAdapter<PanelView, Event> implements SectionIndexer
{
    private HashMap<String, Integer> sectionMap = new HashMap<>();
    private List<String> sections = new ArrayList<>();

    public EventListAdapter(ItemViewBinder<PanelView, Event> itemBinder)
    {
        super(itemBinder);
    }

    @Override
    public Object[] getSections()
    {
        return this.sections.toArray();
    }

    /** First occurrence of section (i) in the list */
    @Override
    public int getPositionForSection(int section)
    {
        String sectionLabel = this.sections.get(section);
        return this.sectionMap.get(sectionLabel);
    }

    /** which section that item (i) belongs to */
    @Override
    public int getSectionForPosition(int i)
    {
        Event item = this.getItem(i);
        String sectionLabel = this.getSectionLabel(item);

        return this.sections.indexOf(sectionLabel);
    }

    @Override
    public void setItems(List<Event> events)
    {
        super.setItems(events);
        this.buildIndex();
    }

    private void buildIndex()
    {
        this.sectionMap.clear();
        List<Event> events = this.getItems();
        for (int position = 0; position < events.size(); position++) {
            Event event = events.get(position);
            String section = this.getSectionLabel(event);

            if (sectionMap.containsKey(section)) {
                continue;
            }
            this.sectionMap.put(section, position);
            this.sections.add(section);
        }
    }

    private String getSectionLabel(Event event)
    {
        DateTimeFormatter format = DateTimeFormat.forPattern("H a");
        String time = format.print(event.getStartDateTime());

        return time;
    }
}
