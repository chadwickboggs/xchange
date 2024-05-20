**States & Transitions**
* [Start] Proposed (User ID, Trade ID)
    * Decline (User ID, Trade ID) -> Declined
    * Accept (User ID, Trade ID) -> Partially Accepted
* Partially Accepted (List of User ID's, Trade ID)
    * Decline (User ID, Trade ID) -> Declined
    * Accept (User ID, Trade ID) -> Accepted
* Accepted (Trade ID)
    * Receive (User ID, Trade ID) -> Partially Received
    * Abandon (User ID, Trade ID) -> Abandoned
* Partially Received (List of User ID's, Trade ID)
    * Receive (User ID, Trade ID) -> Received
    * Abandon (User ID, Trade ID) -> Abandoned
    * Fail (User ID, Trade ID, Reason) -> Failed
* Received (Trade ID)
    * Complete (User ID, Trade ID) -> Partially Completed
    * Fail (User ID, Trade ID, Reason) -> Failed
* Partially Completed (List of User ID's, Trade ID)
    * Complete (User ID, Trade ID) -> Completed
    * Abandon (User ID, Trade ID) -> Abandoned
    * Fail (User ID, Trade ID, Reason) -> Failed
* [End] Completed (Trade ID)
* [End] Declined (List of User ID's, Trade ID)
* [End] Failed (List of User ID's, Trade ID)
* [End] Abandoned (List of User ID's, Trade ID, List of Reasons)
