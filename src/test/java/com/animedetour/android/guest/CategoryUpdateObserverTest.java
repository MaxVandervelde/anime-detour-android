package com.animedetour.android.guest;

import android.content.Context;
import android.view.View;

import com.animedetour.api.guest.model.Category;
import com.animedetour.api.guest.model.Guest;
import com.inkapplications.android.widget.recyclerview.ItemAdapter;
import com.inkapplications.android.widget.recyclerview.SimpleRecyclerView;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;

import monolog.Monolog;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class CategoryUpdateObserverTest {

    private CategoryUpdateObserver categoryUpdateObserver;
    private @Mock SimpleRecyclerView<GuestWidgetView, Guest> mockRecylerView;
    private @Mock Context mockContext;
    private @Mock ItemAdapter<GuestWidgetView, Guest> mockItemAdapter;
    private @Mock View mockEmptyView;
    private @Mock Monolog mockMonolog;

    @Before
    public void initialize() {
        MockitoAnnotations.initMocks(this);

        this.categoryUpdateObserver = new CategoryUpdateObserver(
                mockMonolog,
                mockRecylerView,
                mockEmptyView
        );
    }

    @Test
    public void testSetEmptyViewIfNecessaryWhereListIsEmpty()
    {
        when(this.mockRecylerView.getItemAdapter()).thenReturn(mockItemAdapter);
        this.categoryUpdateObserver.onNext(new ArrayList<Category>());
        verify(mockRecylerView).setVisibility(View.GONE);
        verify(mockEmptyView).setVisibility(View.VISIBLE);
    }

    @Test
    public void testSetEmptyViewIfNecessaryWhereListIsNotEmpty()
    {
        List<Category> nonEmptyList = new ArrayList<>();
        Category testCategory = new Category();
        List<Guest> testGuestList = new ArrayList<>();
        testGuestList.add(new Guest());
        testCategory.setGuests(testGuestList);
        nonEmptyList.add(testCategory);

        when(mockItemAdapter.getItemCount()).thenReturn(1);
        when(this.mockRecylerView.getItemAdapter()).thenReturn(mockItemAdapter);
        this.categoryUpdateObserver.onNext(nonEmptyList);
        verify(mockRecylerView).setVisibility(View.VISIBLE);
        verify(mockEmptyView).setVisibility(View.GONE);
    }

}
