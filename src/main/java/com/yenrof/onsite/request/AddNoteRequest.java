package com.yenrof.onsite.request;

import java.io.Serializable;

import com.yenrof.onsite.dto.NoteDTO;

/**
 * The DTO request class for the Note database table.
 * 
 */
public class AddNoteRequest implements Serializable {
	private static final long serialVersionUID = 1L;
	private long personId;
	private long areaId;
	private NoteDTO note = new NoteDTO();
	public NoteDTO getNoteDTO() {
		return note;
	}
	public void setNote(NoteDTO note) {
		this.note = note;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public long getPersonId() {
		return personId;
	}
	public void setPersonId(long personId) {
		this.personId = personId;
	}
	public long getAreaId() {
		return areaId;
	}
	public void setAreaId(long areaId) {
		this.areaId = areaId;
	}
	}
