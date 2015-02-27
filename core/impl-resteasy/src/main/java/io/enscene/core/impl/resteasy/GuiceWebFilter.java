package io.enscene.core.impl.resteasy;

import javax.servlet.annotation.WebFilter;
import com.google.inject.servlet.GuiceFilter;

@WebFilter(filterName = "guiceFilter", urlPatterns = {"*"})
public class GuiceWebFilter extends GuiceFilter {
}
