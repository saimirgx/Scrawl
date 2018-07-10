package com.example.amantewary.scrawl.Handlers;

public class ShareHandler {
    Integer id;
    Integer share_from;
    Integer share_to;
    Integer note_id;


    public ShareHandler(Integer share_from, Integer share_to, Integer note_id) {
        this.share_from = share_from;
        this.share_to = share_to;
        this.note_id = note_id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getShare_from() {
        return share_from;
    }

    public void setShare_from(Integer share_from) {
        this.share_from = share_from;
    }

    public Integer getShare_to() {
        return share_to;
    }

    public void setShare_to(Integer share_to) {
        this.share_to = share_to;
    }

    public Integer getNote_id() {
        return note_id;
    }

    public void setNote_id(Integer note_id) {
        this.note_id = note_id;
    }

}
