package com.animedetour.android.framework;

import android.app.Application;
import android.content.res.Resources;
import com.animedetour.android.R;
import com.animedetour.android.database.event.EventRepository;
import com.animedetour.api.sched.api.model.Event;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.inkapplications.prism.ApplicationCallback;
import org.apache.commons.logging.Log;
import org.joda.time.DateTime;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

@Singleton
public class InitialDataLoader implements ApplicationCallback
{
    final private ObjectMapper mapper;
    final private Resources resources;
    final private Log logger;
    final private EventRepository eventData;

    @Inject
    public InitialDataLoader(
        ObjectMapper mapper,
        Resources resources,
        Log logger,
        EventRepository eventData
    ) {
        this.mapper = mapper;
        this.resources = resources;
        this.logger = logger;
        this.eventData = eventData;
    }

    @Override
    public void onCreate(Application application)
    {
        try {
            if (false == this.eventData.isEmpty()) {
                return;
            }

            this.logger.debug("Loading initial events");

            InputStream input = this.resources.openRawResource(R.raw.events_201503180428);
            Event[] events = this.mapper.readValue(input, Event[].class);
            for (Event event : events) {
                event.setFetched(new DateTime(2015, 3, 18, 4, 28));
                this.eventData.persist(event);
            }

            this.logger.trace("Finished Loading events");
        } catch (IOException e) {
            this.logger.error("Could not import initial events.", e);
        } catch (SQLException e) {
            this.logger.error("Database Error.", e);
        }
    }
}
