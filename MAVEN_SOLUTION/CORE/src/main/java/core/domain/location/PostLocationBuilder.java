/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.domain.location;

/**
 *
 * @author morei
 */
public class PostLocationBuilder {

    // required parameters
    protected String road;
    protected String number;
    protected String country;
    protected String district;

    // optional parameters
    protected String post_code;

    public PostLocationBuilder(String road, String number, String country, String district) {
        if (road.trim().isEmpty() || number.trim().isEmpty() || country.trim().isEmpty() || district.trim().isEmpty()) {
            throw new IllegalArgumentException("Os parametros têm de ser validos (nao vazios)");
        }
        this.road = road;
        this.number = number;
        this.country = country;
        this.district = district;
    }

    public PostLocationBuilder(String road) {
        if (road.trim().isEmpty()) {
            throw new IllegalArgumentException("Os parametros têm de ser validos (nao vazios)");
        }
        this.road = road;
    }

    public PostLocationBuilder changeRoad(String road) {
        if (road.trim().isEmpty()) {
            throw new IllegalArgumentException("A rua parametro têm de ser valido (nao vazios)");
        }
        this.road = road;
        return this;
    }

    public PostLocationBuilder changeNumber(String number) {
        if (number.trim().isEmpty()) {
            throw new IllegalArgumentException("O number code parametro têm de ser valido (nao vazios)");
        }
        this.number = number;
        return this;
    }

    public PostLocationBuilder changeCountry(String country) {
        if (country.trim().isEmpty()) {
            throw new IllegalArgumentException("O pais code parametro têm de ser valido (nao vazios)");
        }
        this.country = country;
        return this;
    }

    public PostLocationBuilder changeDistrict(String district) {
        if (district.trim().isEmpty()) {
            throw new IllegalArgumentException("O district code parametro têm de ser valido (nao vazios)");
        }
        this.district = district;
        return this;
    }

    public PostLocationBuilder changePost(String post) {
        if (post.trim().isEmpty()) {
            throw new IllegalArgumentException("O postal code parametro têm de ser valido (nao vazios)");
        }
        this.post_code = post;
        return this;
    }

    public PostLocation build() {
        return new PostLocation(this);
    }
}
