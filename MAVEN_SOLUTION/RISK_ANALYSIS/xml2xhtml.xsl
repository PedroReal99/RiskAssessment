<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : xml2xhtml.xsl
    Created on : May 31, 2019, 1:30 PM
    Author     : Ricardo Branco
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:output method="html"/>

    <!-- template surroundings -->
    <xsl:template match="/surroundings">
        <html>
            <head>
                <title>Surroundings</title>
            </head>
            <body>
                <xsl:apply-templates select="surrounding"/>
            </body>
        </html>
    </xsl:template>
    
    <!-- template surrounding -->
    <xsl:template match="surrounding">
        <xsl:apply-templates select="name"/>        
        <xsl:apply-templates select="type"/>
        <xsl:apply-templates select="location"/>
    </xsl:template>
    
    <!-- template name -->
    <xsl:template match="name">
        <h1>
            <b>Nome: <xsl:value-of select="/name"/></b>
        </h1>
    </xsl:template>
    
    <!-- template type -->
    <xsl:template match="type">
        <h2>
            <b>Type: <xsl:value-of select="/type"/></b>
        </h2>
    </xsl:template>
    
    <!-- template location -->
    <xsl:template match="location">
        <h2>
            <b>Location:</b> 
            <xsl:value-of select="/location"/>
        </h2>
        <xsl:apply-templates select="gpslocation"/>
        <xsl:apply-templates select="postlocation"/>       
    </xsl:template>
    
    <!-- template gpslocation -->
    <xsl:template match="gpslocation">
        <h3>
            <b>GPS:</b>
        </h3>
        <xsl:apply-templates select="latitude"/>
        <xsl:apply-templates select="longitude"/>
        <xsl:apply-templates select="altitude"/>
    </xsl:template>
    
    <!-- template latitude -->
    <xsl:template match="latitude">
        <b>Latitude: <xsl:value-of select="latitude"/></b>
    </xsl:template>
    
    <!-- template longitude -->
    <xsl:template match="longitude">
        <b>Longitude: <xsl:value-of select="/longitude"/></b>
    </xsl:template>
    
    <!-- template altitude -->
    <xsl:template match="altitude">
        <b>Altitude: <xsl:value-of select="/altitude"/></b>
    </xsl:template>
    
    <!-- template postlocation -->
    <xsl:template match="postlocation">
        <h3>
            <b>GPS:</b>
        </h3>
        <xsl:apply-templates select="country"/>
        <xsl:apply-templates select="district"/>
        <xsl:apply-templates select="road"/>
        <xsl:apply-templates select="number"/>
        <xsl:apply-templates select="post_code"/>
    </xsl:template>
    
    <!-- template country -->
    <xsl:template match="country">
        <b>Country: <xsl:value-of select="/country"/></b>
    </xsl:template>
    
    <!-- template district -->
    <xsl:template match="district">
        <b>District: <xsl:value-of select="/district"/></b>
    </xsl:template>
    
    <!-- template road -->
    <xsl:template match="road">
        <b>Road: <xsl:value-of select="/road"/></b>
    </xsl:template>
    
    <!-- template number -->
    <xsl:template match="number">
        <b>Number: <xsl:value-of select="/number"/></b>
    </xsl:template>
    
    <!-- template post_code -->
    <xsl:template match="post_code">
        <b>Post_code: <xsl:value-of select="/post_code"/></b>
    </xsl:template>

</xsl:stylesheet>
