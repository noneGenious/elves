package com.Bean;

public class Word
{
	private String wrod;
	private String translate;

	public String getWrod()
	{
		return wrod;
	}

	public Word(String wrod, String translate)
	{
		super();
		this.wrod = wrod;
		this.translate = translate;
	}

	public String getTranslate()
	{
		return translate;
	}

	public void setWrod(String wrod)
	{
		this.wrod = wrod;
	}

	public void setTranslate(String translate)
	{
		this.translate = translate;
	}

}
