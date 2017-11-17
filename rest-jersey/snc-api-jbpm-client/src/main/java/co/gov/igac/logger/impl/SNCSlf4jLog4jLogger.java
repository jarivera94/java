package co.gov.igac.logger.impl;

import java.io.Serializable;
import java.text.MessageFormat;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.slf4j.Marker;
import org.slf4j.helpers.FormattingTuple;
import org.slf4j.helpers.MarkerIgnoringBase;
import org.slf4j.helpers.MessageFormatter;
import org.slf4j.spi.LocationAwareLogger;

import co.gov.igac.entity.Users;



public final class SNCSlf4jLog4jLogger extends MarkerIgnoringBase implements
LocationAwareLogger, ISNCLogger, Serializable {
/**
* 
*/
private static final long serialVersionUID = 6103917514645326394L;

public static final String FORMATO_MENSAJE = "[identificador: {0}, Territorial: {1}, Usuario: {2}]-{3}";

final transient Logger logger;
static final String FQCN = SNCSlf4jLog4jLogger.class.getName();
final boolean traceCapable;

public SNCSlf4jLog4jLogger(Logger logger) {
this.logger = logger;
this.name = logger.getName();
this.traceCapable = isTraceCapable();
}

private boolean isTraceCapable() {
try {
	this.logger.isTraceEnabled();
	return true;
} catch (NoSuchMethodError e) {
}
return false;
}

public boolean isTraceEnabled() {
if (this.traceCapable) {
	return this.logger.isTraceEnabled();
}
return this.logger.isDebugEnabled();
}

public void trace(String msg) {
this.logger.log(FQCN, this.traceCapable ? Level.TRACE : Level.DEBUG,
		msg, null);
}

public void trace(String format, Object arg) {
if (isTraceEnabled()) {
	FormattingTuple ft = MessageFormatter.format(format, arg);
	this.logger.log(FQCN,
			this.traceCapable ? Level.TRACE : Level.DEBUG, ft
					.getMessage(), ft.getThrowable());
}
}

public void trace(String format, Object arg1, Object arg2) {
if (isTraceEnabled()) {
	FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
	this.logger.log(FQCN,
			this.traceCapable ? Level.TRACE : Level.DEBUG, ft
					.getMessage(), ft.getThrowable());
}
}

public void trace(String format, Object... params) {
if (isTraceEnabled()) {
	FormattingTuple ft = MessageFormatter.arrayFormat(format, params);
	this.logger.log(FQCN,
			this.traceCapable ? Level.TRACE : Level.DEBUG, ft
					.getMessage(), ft.getThrowable());
}
}

public void trace(String msg, Throwable t) {
this.logger.log(FQCN, this.traceCapable ? Level.TRACE : Level.DEBUG,
		msg, t);
}

@Override
public void trace(String msg, Users usuario, long id) {
	String mensaje = MessageFormat.format(FORMATO_MENSAJE, id, usuario
			.getUsername(),
			msg);
this.logger.log(FQCN, Level.DEBUG, mensaje, null);
}

@Override
public void trace(String msg, Throwable t, Users usuario, long id) {
String mensaje = MessageFormat.format(FORMATO_MENSAJE, id, usuario
		.getUsername(),
		msg);
this.logger.log(FQCN, Level.DEBUG, mensaje, t);
}

public boolean isDebugEnabled() {
return this.logger.isDebugEnabled();
}

public void debug(String msg) {
this.logger.log(FQCN, Level.DEBUG, msg, null);
}

public void debug(String format, Object arg) {
if (this.logger.isDebugEnabled()) {
	FormattingTuple ft = MessageFormatter.format(format, arg);
	this.logger.log(FQCN, Level.DEBUG, ft.getMessage(), ft
			.getThrowable());
}
}

public void debug(String format, Object arg1, Object arg2) {
if (this.logger.isDebugEnabled()) {
	FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
	this.logger.log(FQCN, Level.DEBUG, ft.getMessage(), ft
			.getThrowable());
}
}

public void debug(String format, Object... params) {
if (this.logger.isDebugEnabled()) {
	FormattingTuple ft = MessageFormatter.arrayFormat(format, params);
	this.logger.log(FQCN, Level.DEBUG, ft.getMessage(), ft
			.getThrowable());
}
}

public void debug(String msg, Throwable t) {
this.logger.log(FQCN, Level.DEBUG, msg, t);
}

@Override
public void debug(String msg, Users usuario, long id) {
	String mensaje = MessageFormat.format(FORMATO_MENSAJE, id, usuario
			.getUsername(),
			msg);
this.logger.log(FQCN, Level.DEBUG, mensaje, null);
}

@Override
public void debug(String msg, Throwable t, Users usuario, long id) {
	String mensaje = MessageFormat.format(FORMATO_MENSAJE, id, usuario
			.getUsername(),
			msg);
this.logger.log(FQCN, Level.DEBUG, mensaje, t);
}

public boolean isInfoEnabled() {
return this.logger.isInfoEnabled();
}

public void info(String msg) {
	
	this.logger.log(FQCN, Level.INFO, msg, null);
}

public void info(String format, Object arg) {
if (this.logger.isInfoEnabled()) {
	FormattingTuple ft = MessageFormatter.format(format, arg);
	this.logger.log(FQCN, Level.INFO, ft.getMessage(), ft
			.getThrowable());
}
}

public void info(String format, Object arg1, Object arg2) {
if (this.logger.isInfoEnabled()) {
	FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
	this.logger.log(FQCN, Level.INFO, ft.getMessage(), ft
			.getThrowable());
}

}

public void info(String format, Object... params) {
if (this.logger.isInfoEnabled()) {
	FormattingTuple ft = MessageFormatter.arrayFormat(format, params);
	this.logger.log(FQCN, Level.INFO, ft.getMessage(), ft
			.getThrowable());
}
}

public void info(String msg, Throwable t) {
this.logger.log(FQCN, Level.INFO, msg, t);
}

@Override
public void info(String msg, Users usuario, long id) {
	String mensaje = MessageFormat.format(FORMATO_MENSAJE, id, usuario
			.getUsername(),
			msg);
this.logger.log(FQCN, Level.DEBUG, mensaje, null);

}

@Override
public void info(String msg, Throwable t, Users usuario, long id) {
	String mensaje = MessageFormat.format(FORMATO_MENSAJE, id, usuario
			.getUsername(),
			msg);
this.logger.log(FQCN, Level.DEBUG, mensaje, t);
}

public boolean isWarnEnabled() {
return this.logger.isEnabledFor(Level.WARN);
}

public void warn(String msg) {
this.logger.log(FQCN, Level.WARN, msg, null);
}

public void warn(String format, Object arg) {
if (this.logger.isEnabledFor(Level.WARN)) {
	FormattingTuple ft = MessageFormatter.format(format, arg);
	this.logger.log(FQCN, Level.WARN, ft.getMessage(), ft
			.getThrowable());
}
}

public void warn(String format, Object arg1, Object arg2) {
if (this.logger.isEnabledFor(Level.WARN)) {
	FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
	this.logger.log(FQCN, Level.WARN, ft.getMessage(), ft
			.getThrowable());
}
}

public void warn(String format, Object... params) {
if (this.logger.isEnabledFor(Level.WARN)) {
	FormattingTuple ft = MessageFormatter.arrayFormat(format, params);
	this.logger.log(FQCN, Level.WARN, ft.getMessage(), ft
			.getThrowable());
}
}

public void warn(String msg, Throwable t) {
this.logger.log(FQCN, Level.WARN, msg, t);
}

@Override
public void warn(String msg, Users usuario, long id) {
	String mensaje = MessageFormat.format(FORMATO_MENSAJE, id, usuario
			.getUsername(),
			msg);
this.logger.log(FQCN, Level.DEBUG, mensaje, null);
}

@Override
public void warn(String msg, Throwable t, Users usuario, long id) {
	String mensaje = MessageFormat.format(FORMATO_MENSAJE, id, usuario
			.getUsername(),
			msg);
this.logger.log(FQCN, Level.DEBUG, mensaje, t);
}

public boolean isErrorEnabled() {
return this.logger.isEnabledFor(Level.ERROR);
}

public void error(String msg) {
this.logger.log(FQCN, Level.ERROR, msg, null);
}

public void error(String format, Object arg) {
if (this.logger.isEnabledFor(Level.ERROR)) {
	FormattingTuple ft = MessageFormatter.format(format, arg);
	this.logger.log(FQCN, Level.ERROR, ft.getMessage(), ft
			.getThrowable());
}
}

public void error(String format, Object arg1, Object arg2) {
if (this.logger.isEnabledFor(Level.ERROR)) {
	FormattingTuple ft = MessageFormatter.format(format, arg1, arg2);
	this.logger.log(FQCN, Level.ERROR, ft.getMessage(), ft
			.getThrowable());
}
}

@Override
public void error(String format, Object... params) {
if (this.logger.isEnabledFor(Level.ERROR)) {
	FormattingTuple ft = MessageFormatter.arrayFormat(format, params);
	this.logger.log(FQCN, Level.ERROR, ft.getMessage(), ft
			.getThrowable());
}
}

public void error(String msg, Throwable t) {
this.logger.log(FQCN, Level.ERROR, msg, t);
}

@Override
public void error(String msg, Users usuario, long id) {
	String mensaje = MessageFormat.format(FORMATO_MENSAJE, id, usuario
			.getUsername(),
			msg);
this.logger.log(FQCN, Level.ERROR, mensaje, null);
}

@Override
public void error(String msg, Throwable t, Users usuario, long id) {
	String mensaje = MessageFormat.format(FORMATO_MENSAJE, id, usuario
			.getUsername(),
			msg);
this.logger.log(FQCN, Level.ERROR, mensaje, t);
}

@Override
public void fatal(String msg) {
this.logger.log(FQCN, Level.FATAL, msg, null);

}

@Override
public void fatal(String msg, Throwable t) {
this.logger.log(FQCN, Level.FATAL, msg, t);
}

@Override
public void fatal(String msg, Users usuario, long id) {
	String mensaje = MessageFormat.format(FORMATO_MENSAJE, id, usuario
			.getUsername(),
			msg);
this.logger.log(FQCN, Level.DEBUG, mensaje, null);
}

@Override
public void fatal(String msg, Throwable t, Users usuario, long id) {
	String mensaje = MessageFormat.format(FORMATO_MENSAJE, id, usuario
			.getUsername(),
			msg);
this.logger.log(FQCN, Level.DEBUG, mensaje, t);
}

public void log(Marker marker, String callerFQCN, int level, String msg,
	Object[] argArray, Throwable t) {
Level log4jLevel;
switch (level) {
case 0:
	log4jLevel = this.traceCapable ? Level.TRACE : Level.DEBUG;
	break;
case 10:
	log4jLevel = Level.DEBUG;
	break;
case 20:
	log4jLevel = Level.INFO;
	break;
case 30:
	log4jLevel = Level.WARN;
	break;
case 40:
	log4jLevel = Level.ERROR;
	break;

case 50:
	log4jLevel = Level.FATAL;
	break;
default:
	throw new IllegalStateException("Level number " + level
			+ " is not recognized.");
}
this.logger.log(callerFQCN, log4jLevel, msg, t);
}
}