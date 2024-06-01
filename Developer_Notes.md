**Interesting Details**
* Multi-participant trades are supported for any number of participants/users/items.
* All two party (two item / two user) trades are modeled by one `Trade` record referencing the two item involved.
* All individual `Trade` records have one parent, "composite" trade record.
* When multiple `Trade` records share a parent, "composite" trade record, they are part of a "composite" trade.  The "composite" trade records tracks the progress of the complete trade.  For example, if any user abandons one of their participating individual trades, then the full "composite" trade becomes abandoned.  When all users have completed their participating individual trades, then the full "composite" trade becomes completed.
* Some trade state transitions are conditional on all participants completing their part.  Said conditional transitions use the `Transition.condition` `Predicate` of the Finite State Machines's `Transition` object.
