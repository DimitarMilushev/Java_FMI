package com.company.warehouse;

import com.company.warehouse.exceptions.CapacityExceededException;
import com.company.warehouse.exceptions.ParcelNotFoundException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class MJTExpressWarehouse<L, P> implements DeliveryServiceWarehouse<L, P> {

    private final String PARCEL_LIST_FULL = "Parcel list capacity exceeded.";
    private final String PARCEL_NOT_FOUND = "No such parcel in list.";
    private final String IS_INVALID = " is invalid.";

    private final int capacity;
    private final int retentionPeriod;
    private final Map<MJTLabel<L>, P> parcelsList;

    public MJTExpressWarehouse(int capacity, int retentionPeriod) {
        this.capacity = capacity;
        this.retentionPeriod = retentionPeriod;
        this.parcelsList = new HashMap<>();

    }

    @Override
    public void submitParcel(L label, P parcel, LocalDateTime submissionDate)
            throws CapacityExceededException {

        checkIfElementIsNull("Label", label);
        checkIfElementIsNull("Parcel", parcel);

        if (submissionDate.isAfter(LocalDateTime.now())) {

            throw new IllegalArgumentException("Date cannot be set in the future.");
        }

        if (parcelsList.size() > capacity) {
            throw new CapacityExceededException(PARCEL_LIST_FULL);
        }

        this.parcelsList.put(new MJTLabel<>(label, submissionDate), parcel);
    }

    @Override
    public P getParcel(L label) {
        checkIfElementIsNull("Label", label);

        return this.parcelsList.get(label);
    }

    @Override
    public P deliverParcel(L label) throws ParcelNotFoundException {
        checkIfElementIsNull("Label", label);

        P parcel = this.parcelsList.getOrDefault(label, null);
        if (parcel == null) {
            throw new ParcelNotFoundException("Parcel with the given label does not exist in the warehouse.");
        }

        this.parcelsList.remove(label);

        return parcel;
    }

    @Override
    public double getWarehouseSpaceLeft() {
        return 1 - (double)(this.parcelsList.size() / this.capacity);
    }

    @Override
    public Map<L, P> getWarehouseItems() {
        Map<L, P> allItems = new HashMap<>();

        for (Map.Entry<MJTLabel<L>, P> entry : this.parcelsList.entrySet()) {
            allItems.put(entry.getKey().getLabel(), entry.getValue());
        }

        return allItems;
    }

    @Override
    public Map<L, P> deliverParcelsSubmittedBefore(LocalDateTime before) {
        this.checkIfElementIsNull("Date before", before);
        Map<L, P> allItems = new HashMap<>();

        for (Map.Entry<MJTLabel<L>, P> entry : this.parcelsList.entrySet()) {
            if (entry.getKey().getSubmissionDate().isBefore(before)) {

                allItems.put(entry.getKey().getLabel(), entry.getValue());
                this.parcelsList.remove(entry.getKey().getLabel());
            }
        }

        return allItems;
    }

    @Override
    public Map<L, P> deliverParcelsSubmittedAfter(LocalDateTime after) {
        this.checkIfElementIsNull("Date after", after);

        Map<L, P> allItems = new HashMap<>();

        for (Map.Entry<MJTLabel<L>, P> entry : this.parcelsList.entrySet()) {
            if (after.isBefore(entry.getKey().getSubmissionDate())) {

                allItems.put(entry.getKey().getLabel(), entry.getValue());
                this.parcelsList.remove(entry.getKey().getLabel());
            }
        }

        return allItems;
    }

    private void checkIfElementIsNull(String name, Object element) throws IllegalArgumentException {

        if (element == null) {
            throw new IllegalArgumentException(name + this.IS_INVALID);
        }
    }
}