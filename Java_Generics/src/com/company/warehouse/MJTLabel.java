package com.company.warehouse;

import java.time.LocalDateTime;
import java.util.Objects;

public class MJTLabel<L> implements Comparable<MJTLabel<L>> {
    private final L label;
    private final LocalDateTime submissionDate;

    public MJTLabel(L label, LocalDateTime submissionDate) {
        this.label = label;
        this.submissionDate = submissionDate;
    }


    @Override
    public int compareTo(MJTLabel<L> o) {
        if(this.submissionDate != null && o.submissionDate != null) {
            int comparison = this.submissionDate.compareTo(o.submissionDate);

            if(comparison != 0) {
                return this.submissionDate.compareTo(o.submissionDate);
            }
        }

        if(this.label != null && o.label != null && this.label.equals(o.label)) {
            return 0;
        }

        return 1;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) {
            return true;
        }

        if(!(o instanceof MJTLabel)) {
            return false;
        }

        MJTLabel<?> that = (MJTLabel<?>) o;
        return Objects.equals(label, that.label);
    }

    public LocalDateTime getSubmissionDate() {
        return this.submissionDate;
    }

    public L getLabel() {
        return this.label;
    }
}
